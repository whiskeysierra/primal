package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;
import org.whiskeysierra.primal.Stream;
import org.whiskeysierra.primal.Stream.Input;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.Map;

final class DefaultManagedProcess implements ManagedProcess {

    // TODO set mode to "executable"
    public DefaultManagedProcess(Path executable) {
        throw new UnsupportedOperationException();
    }

    // TODO set mode to "command"
    public DefaultManagedProcess(String command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess parameterize(Object... arguments) {
        // TODO delegate to parameterize(Arrays.asList(arguments))
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess parameterize(Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess in(Path directory) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess with(String variable, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess with(Map<String, String> properties) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess redirect(Stream stream, Redirect redirect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess redirectErrorStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess consume(Input input) {
        // TODO delegate to consume(Collections.singleton(input))
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess consume(Input... inputs) {
        // TODO delegate to consume(Arrays.asList(inputs))
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess consume(Iterable<Input> inputs) {
        // TODO on unix, just redirect with Redirect.to(Paths.get("/dev/null").toFile())
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess allow(int exitValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess allow(int... exitValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RunningProcess call() throws IOException {
        throw new UnsupportedOperationException();
    }

}
