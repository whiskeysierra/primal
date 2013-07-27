package org.whiskeysierra.process.internal.manage;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.Stream;
import org.whiskeysierra.process.internal.Default;
import org.whiskeysierra.process.spi.AccessibleManagedProcess;
import org.whiskeysierra.process.spi.ProcessExecutor;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

final class DefaultManagedProcess implements ManagedProcess, AccessibleManagedProcess {

    private final ProcessExecutor executor;

    private Path executable;
    private String command;

    private final List<Object> arguments = Lists.newArrayList();
    private Path directory = Paths.get("");

    private final Map<String, String> environment = Maps.newHashMap(System.getenv());

    private final Map<Stream, Redirection> redirections;
    private int[] allowedExitValues = {0};

    @Inject
    DefaultManagedProcess(ProcessExecutor executor, @Default Map<Stream, Redirection> defaults) {
        this.executor = executor;
        this.redirections = defaults;
    }

    @Override
    public ManagedProcess setExecutable(Path executable) {
        // TODO null check
        this.executable = executable;
        this.command = null;
        return this;
    }

    @Override
    public Path getExecutable() {
        return executable;
    }

    @Override
    public ManagedProcess setCommand(String command) {
        // TODO null check
        this.executable = null;
        this.command = command;
        return this;
    }

    @Override
    public String getCommand() {
        return command;
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
    public List<Object> getArguments() {
        return arguments;
    }

    @Override
    public ManagedProcess in(Path directory) {
        // TODO null check
        this.directory = directory;
        return this;
    }


    @Override
    public Path getDirectory() {
        return directory;
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
    public Map<String, String> getEnvironment() {
        return environment;
    }

    @Override
    public ManagedProcess redirect(Stream stream, Redirection redirect) {
        // TODO null checks
        // TODO check arguments
        redirections.put(stream, redirect);
        return this;
    }

    @Override
    public Redirection getRedirection(Stream stream) {
        // TODO null check
        return redirections.get(stream);
    }

    @Override
    public ManagedProcess allow(int exitValue) {
        this.allowedExitValues = new int[]{exitValue};
        return this;
    }

    // TODO specify defensive copy
    @Override
    public ManagedProcess allow(int... exitValues) {
        // TODO null checks
        // TODO sort them, to support binary search (either here, or anywhere later down the call chain)
        this.allowedExitValues = Arrays.copyOf(exitValues, exitValues.length);
        return this;
    }

    @Override
    public int[] getAllowedExitValues() {
        return allowedExitValues;
    }

    @Override
    public RunningProcess call() throws IOException {
        return executor.execute(this);
    }

    @Override
    public String toString() {
        // TODO define a decent string representation
        throw new UnsupportedOperationException();
    }
}
