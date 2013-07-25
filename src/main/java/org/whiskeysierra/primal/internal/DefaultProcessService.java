package org.whiskeysierra.primal.internal;

import com.google.common.base.Preconditions;
import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.ProcessService;

import java.nio.file.Path;

public final class DefaultProcessService implements ProcessService {

    @Override
    public ManagedProcess prepare(Path executable, Object... arguments) {
        Preconditions.checkNotNull(executable, "Executable");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(executable).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(String command, Object... arguments) {
        Preconditions.checkNotNull(command, "Command");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(command).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(Path executable, Iterable<?> arguments) {
        Preconditions.checkNotNull(executable, "Executable");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(executable).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(String command, Iterable<?> arguments) {
        Preconditions.checkNotNull(command, "Command");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(command).parameterize(arguments);
    }

}
