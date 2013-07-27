package org.whiskeysierra.primal.internal;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;
import org.whiskeysierra.primal.Stream;
import org.whiskeysierra.primal.Stream.Output;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

final class DefaultManagedProcess implements ManagedProcess {

    private final Executor executor;

    private Path executable;
    private String command;

    private final List<Object> arguments = Lists.newArrayList();
    private Path directory;

    private final Map<String, String> environment = Maps.newHashMap(System.getenv());

    private final Map<Stream, Redirect> redirects = Redirects.getDefaults();
    private boolean redirectErrorStream;
    private boolean noInput;

    @Inject
    DefaultManagedProcess(Executor executor) {
        this.executor = executor;
    }

    @Override
    public ManagedProcess setExecutable(Path executable) {
        // TODO null check
        this.executable = executable;
        this.command = null;
        return this;
    }

    @Override
    public ManagedProcess setCommand(String command) {
        // TODO null check
        this.executable = null;
        this.command = command;
        return this;
    }

    @Override
    public ManagedProcess parameterize(Object... arguments) {
        // TODO null check
        return parameterize(Arrays.asList(arguments));
    }

    @Override
    public ManagedProcess parameterize(Iterable<?> arguments) {
        // TODO null check
        this.arguments.clear();
        Iterables.addAll(this.arguments, arguments);
        return this;
    }

    @Override
    public ManagedProcess in(Path directory) {
        // TODO null check
        this.directory = directory;
        return this;
    }

    @Override
    public ManagedProcess with(String variable, String value) {
        // TODO null checks
        this.environment.put(variable, value);
        return this;
    }

    // TODO specifify semantics: overwrite vs. append
    // TODO if its overwrite, how do you append?
    @Override
    public ManagedProcess with(Map<String, String> properties) {
        // TODO null checks
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess redirect(Stream stream, Redirect redirect) {
        // TODO null checks
        this.redirects.put(stream, redirect);
        return this;
    }

    // TODO specify semantics when redirecting manually using redirect(Stream.ERROR)
    @Override
    public ManagedProcess redirectErrorStream() {
        this.redirectErrorStream = true;
        return this;
    }

    @Override
    public ManagedProcess noInput() {
        // TODO alternatively, use Redirects.fromNullDevice(), if available
        this.noInput = true;
        return this;
    }

    @Override
    public ManagedProcess consume(Output output) {
        // TODO null checks
        return consume(Collections.singleton(output));
    }

    @Override
    public ManagedProcess consume(Output... outputs) {
        // TODO null checks
        return consume(Arrays.asList(outputs));
    }

    @Override
    public ManagedProcess consume(Iterable<Output> outputs) {
        // TODO null checks
        // TODO on unix, just redirect with Redirect.to(Paths.get("/dev/null").toFile())
        // TODO check if NUL on windows works as well
        // TODO fall back to stream gobbling, if no null device is available

        for (Output output : outputs) {
            final Redirect redirect = Redirects.toNullDevice();

            if (redirect == null) {
                // TODO use StreamGobbler
            } else {
                redirects.put(output, redirect);
            }

            break;
        }

        return this;
    }

    @Override
    public ManagedProcess allow(int exitValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess allow(int... exitValues) {
        // TODO null checks
        throw new UnsupportedOperationException();
    }

    @Override
    public RunningProcess call() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }

}
