package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;
import org.whiskeysierra.primal.Stream;

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
    public ManagedProcess gobble(Stream stream) {
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
