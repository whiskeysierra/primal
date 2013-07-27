package org.whiskeysierra.process.internal.execute;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Redirection.Type;
import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.Stream;
import org.whiskeysierra.process.internal.Exceptions;
import org.whiskeysierra.process.spi.AccessibleManagedProcess;
import org.whiskeysierra.process.spi.EscapeStrategy;
import org.whiskeysierra.process.spi.InputStreamHandler;
import org.whiskeysierra.process.spi.OutputStreamHandler;
import org.whiskeysierra.process.spi.ProcessExecutor;
import org.whiskeysierra.process.spi.ProcessFactory;
import org.whiskeysierra.process.spi.Redirector;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.concurrent.Executor;

final class DefaultProcessExecutor implements ProcessExecutor {

    private final Redirector redirector;
    private final Executor executor;
    private final EscapeStrategy strategy;
    private final ProcessFactory factory;

    @Inject
    DefaultProcessExecutor(Redirector redirector, Executor executor, EscapeStrategy strategy, ProcessFactory factory) {
        this.redirector = redirector;
        this.executor = executor;
        this.strategy = strategy;
        this.factory = factory;
    }

    @Override
    public RunningProcess execute(AccessibleManagedProcess managed) throws IOException {
        final ProcessBuilder builder = new ProcessBuilder();

        final String program;

        // TODO this is ugly, can we have it nicer?!
        if (managed.getExecutable() == null) {
            program = strategy.escape(managed.getCommand());
        } else if (managed.getCommand() == null) {
            program = strategy.escape(managed.getExecutable());
        } else {
            throw new IllegalStateException();
        }

        final List<String> arguments = strategy.escape(managed.getArguments());

        final List<String> command = Lists.newArrayListWithCapacity(arguments.size() + 1);

        command.add(program);
        command.addAll(arguments);

        builder.command(command);

        builder.directory(managed.getDirectory().toFile());
        builder.environment().putAll(managed.getEnvironment());

        // TODO maybe we need a separate step, just finding out the action, than later on, we select a handler
        final OutputStreamHandler inputHandler = getInputHandler(managed, builder);
        final InputStreamHandler outputHandler = getOutputHandler(managed, builder);
        final InputStreamHandler errorHandler = getErrorHandler(managed, builder);

        int blockingUserOperations = 0;
        int blockingLibraryOperations = 0;

        final boolean gobblingOutput = outputHandler == InputStreamHandlers.GOBBLE;
        final boolean gobblingError = errorHandler == InputStreamHandlers.GOBBLE;

        final boolean writingToInput = managed.getRedirection(Stream.INPUT).type() == Type.PIPE;
        final boolean readingFromOutput = managed.getRedirection(Stream.OUTPUT).type() == Type.PIPE;
        final boolean readingFromError = managed.getRedirection(Stream.ERROR).type() == Type.PIPE;

        if (gobblingOutput && gobblingError) {
            // redirect error into output, so we just need to gobble one stream
            // should save us a thread
            builder.redirectErrorStream(true);
            // TODO set errorHandler back to NOOP
            blockingLibraryOperations += 1;
        } else {
            if (readingFromOutput) {
                blockingUserOperations += 1;
            } else if (gobblingOutput) {
                blockingLibraryOperations += 1;
            }

            if (readingFromError) {
                blockingUserOperations += 1;
            } else if (gobblingError) {
                blockingLibraryOperations += 1;
            }
        }

        if (writingToInput) {
            blockingUserOperations += 1;
        }

        // the user might end up using all streams
        assert blockingUserOperations <= 3;
        // at a maximum, we have one gobbler running
        assert blockingLibraryOperations <= 1;

        if (blockingLibraryOperations == 1) {
            if (blockingUserOperations == 0) {
                // TODO we could use the current thread here
            } else if (executor == null) {
                // we defenitely need a thread pool for the gobbler, but we didn't get one
                // TODO throw meaningful exception when requesting a gobbler without thread pool
                throw new IllegalStateException();
            }
        }

        final Process process = builder.start();
        // TODO we need to include the execute, maybe...
        handleStreams(process, inputHandler, outputHandler, errorHandler);

        return factory.create(process, managed.getAllowedExitValues());
    }

    private void handleStreams(Process process, OutputStreamHandler inputHandler, InputStreamHandler outputHandler,
        InputStreamHandler errorHandler) throws IOException {

        Exception thrown = null;

        try {
            inputHandler.handle(process.getOutputStream());
        } catch (Exception e) {
            thrown = null;
        } finally {
            try {
                outputHandler.handle(process.getInputStream());
            } catch (Exception e) {
                thrown = Objects.firstNonNull(thrown, e);
            } finally {
                try {
                    errorHandler.handle(process.getErrorStream());
                } catch (Exception e) {
                    thrown = Objects.firstNonNull(thrown, e);
                }
            }
        }

        if (thrown == null) {
            return;
        }

        Throwables.propagateIfInstanceOf(thrown, IOException.class);
        throw Exceptions.sneakyThrow(thrown);
    }

    private OutputStreamHandler getInputHandler(AccessibleManagedProcess managed, ProcessBuilder builder) {
        final Redirection redirection = managed.getRedirection(Stream.INPUT);

        switch (redirection.type()) {
            case PIPE: {
                builder.redirectInput(Redirect.PIPE);
                return OutputStreamHandlers.NOOP;
            }
            case INHERIT: {
                builder.redirectInput(Redirect.INHERIT);
                return OutputStreamHandlers.NOOP;
            }
            case READ: {
                builder.redirectInput(redirection.path().toFile());
                return OutputStreamHandlers.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.fromNullDevice();

                if (redirect == null) {
                    return OutputStreamHandlers.CLOSE;
                } else {
                    builder.redirectInput(redirect);
                    return OutputStreamHandlers.NOOP;
                }
            }
            default: {
                throw new AssertionError("Can't redirect " + Stream.INPUT + " using " + redirection);
            }
        }
    }

    private InputStreamHandler getOutputHandler(AccessibleManagedProcess managed, ProcessBuilder builder) {
        final Redirection redirection = managed.getRedirection(Stream.OUTPUT);

        switch (redirection.type()) {
            case PIPE: {
                builder.redirectOutput(Redirect.PIPE);
                return InputStreamHandlers.NOOP;
            }
            case INHERIT: {
                builder.redirectOutput(Redirect.INHERIT);
                return InputStreamHandlers.NOOP;
            }
            case APPEND: {
                builder.redirectOutput(Redirect.appendTo(redirection.path().toFile()));
                return InputStreamHandlers.NOOP;
            }
            case WRITE: {
                builder.redirectOutput(redirection.path().toFile());
                return InputStreamHandlers.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.toNullDevice();

                if (redirect == null) {
                    return InputStreamHandlers.GOBBLE;
                } else {
                    builder.redirectOutput(redirect);
                    return InputStreamHandlers.NOOP;
                }
            }
            default: {
                throw new AssertionError("Can't redirect " + Stream.OUTPUT + " using " + redirection);
            }
        }
    }

    private InputStreamHandler getErrorHandler(AccessibleManagedProcess managed, ProcessBuilder builder) {
        final Redirection redirection = managed.getRedirection(Stream.ERROR);

        switch (redirection.type()) {
            case PIPE: {
                builder.redirectInput(Redirect.PIPE);
                return InputStreamHandlers.NOOP;
            }
            case INHERIT: {
                builder.redirectError(Redirect.INHERIT);
                return InputStreamHandlers.NOOP;
            }
            case APPEND: {
                builder.redirectError(Redirect.appendTo(redirection.path().toFile()));
                return InputStreamHandlers.NOOP;
            }
            case WRITE: {
                builder.redirectError(redirection.path().toFile());
                return InputStreamHandlers.NOOP;
            }
            case OUTPUT: {
                builder.redirectErrorStream(true);
                return InputStreamHandlers.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.toNullDevice();

                if (redirect == null) {
                    return InputStreamHandlers.GOBBLE;
                } else {
                    builder.redirectError(redirect);
                    return InputStreamHandlers.NOOP;
                }
            }
            default: {
                throw new AssertionError("Can't redirect " + Stream.ERROR + " using " + redirection);
            }
        }
    }

}
