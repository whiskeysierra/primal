package org.whiskeysierra.primal.internal;

import com.google.common.base.Joiner;
import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.Map;

final class DefaultManagedProcess implements ManagedProcess {

    private final ProcessBuilder builder = new ProcessBuilder();

    public DefaultManagedProcess(Path executable) {

    }

    public DefaultManagedProcess(String command) {

    }

    @Override
    public ManagedProcess parameterize(Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess parameterize(Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess in(Path directory) {
        builder.directory(directory.toFile());
        return this;
    }

    @Override
    public ManagedProcess with(String variable, String value) {
        builder.environment().put(variable, value);
        return this;
    }

    @Override
    public ManagedProcess with(Map<String, String> properties) {
        builder.environment().putAll(properties);
        return this;
    }

    @Override
    public RunningProcess call() throws IOException{
        return new DefaultRunningProcess(builder);
    }

    @Override
    public String toString() {
        return Joiner.on(' ').join(builder.command()) + " [new]";
    }

}
