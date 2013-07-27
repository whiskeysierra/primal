package org.whiskeysierra.process.internal;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Redirection.Type;
import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.Stream;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.concurrent.Executor;

final class DefaultProcessExecutor implements ProcessExecutor {

    private final Redirector redirector;
    private final Executor executor;

    @Inject
    DefaultProcessExecutor(Redirector redirector, Executor executor) {
        this.redirector = redirector;
        this.executor = executor;
    }

    @Override
    public RunningProcess execute(AccessibleManagedProcess managed) throws IOException {
        final ProcessBuilder builder = new ProcessBuilder();

        // TODO escape managed.getExecutable/Command + managed.getArguments
        final List<String> command = null;

        builder.command(command);

        builder.directory(managed.getDirectory().toFile());
        builder.environment().putAll(managed.getEnvironment());

        final OutputStreamHandler inputHandler = getInputHandler(managed, builder);
        final InputStreamHandler outputHandler = getOutputHandler(managed, builder);
        final InputStreamHandler errorHandler = getErrorHandler(managed, builder);

        int blockingUserOperations = 0;
        int blockingLibraryOperations = 0;

        final boolean gobblingOutput = outputHandler == InputStreamHandler.GOBBLE;
        final boolean gobblingError = errorHandler == InputStreamHandler.GOBBLE;

        final boolean writingToInput = managed.getRedirection(Stream.INPUT).type() == Type.PIPE;
        final boolean readingFromOutput = managed.getRedirection(Stream.OUTPUT).type() == Type.PIPE;
        final boolean readingFromError = managed.getRedirection(Stream.ERROR).type() == Type.PIPE;

        if (gobblingOutput && gobblingError) {
            // redirect error into output, so we just need to gobble one stream
            // should save us a thread
            builder.redirectErrorStream(true);
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
            } else {
                // TODO now we defenitely need a thread pool for the gobbler
            }
        }

        // TODO throw meaningful exception when requesting a gobbler without thread pool

        final Process process = builder.start();
        handleStreams(process, inputHandler, outputHandler, errorHandler);


        return new DefaultRunningProcess(process, managed.getAllowedExitValues());
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

        Throwables.propagateIfInstanceOf(thrown, IOException.class);
        throw Exceptions.sneakyThrow(thrown);
    }

    private OutputStreamHandler getInputHandler(AccessibleManagedProcess managed, ProcessBuilder builder) {
        final Redirection redirection = managed.getRedirection(Stream.INPUT);

        switch (redirection.type()) {
            case PIPE: {
                builder.redirectInput(Redirect.PIPE);
                return OutputStreamHandler.NOOP;
            }
            case INHERIT: {
                builder.redirectInput(Redirect.INHERIT);
                return OutputStreamHandler.NOOP;
            }
            case READ: {
                builder.redirectInput(redirection.path().toFile());
                return OutputStreamHandler.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.fromNullDevice();

                if (redirect == null) {
                    return OutputStreamHandler.CLOSE;
                } else {
                    builder.redirectInput(redirect);
                    return OutputStreamHandler.NOOP;
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
                return InputStreamHandler.NOOP;
            }
            case INHERIT: {
                builder.redirectOutput(Redirect.INHERIT);
                return InputStreamHandler.NOOP;
            }
            case APPEND: {
                builder.redirectOutput(Redirect.appendTo(redirection.path().toFile()));
                return InputStreamHandler.NOOP;
            }
            case WRITE: {
                builder.redirectOutput(redirection.path().toFile());
                return InputStreamHandler.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.toNullDevice();

                if (redirect == null) {
                    return InputStreamHandler.GOBBLE;
                } else {
                    builder.redirectOutput(redirect);
                    return InputStreamHandler.NOOP;
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
                return InputStreamHandler.NOOP;
            }
            case INHERIT: {
                builder.redirectError(Redirect.INHERIT);
                return InputStreamHandler.NOOP;
            }
            case APPEND: {
                builder.redirectError(Redirect.appendTo(redirection.path().toFile()));
                return InputStreamHandler.NOOP;
            }
            case WRITE: {
                builder.redirectError(redirection.path().toFile());
                return InputStreamHandler.NOOP;
            }
            case OUTPUT: {
                builder.redirectErrorStream(true);
                return InputStreamHandler.NOOP;
            }
            case NULL: {
                final Redirect redirect = redirector.toNullDevice();

                if (redirect == null) {
                    return InputStreamHandler.GOBBLE;
                } else {
                    builder.redirectError(redirect);
                    return InputStreamHandler.NOOP;
                }
            }
            default: {
                throw new AssertionError("Can't redirect " + Stream.ERROR + " using " + redirection);
            }
        }
    }

}
