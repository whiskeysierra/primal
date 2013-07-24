package org.whiskeysierra.primal;

import com.google.common.base.Preconditions;
import org.whiskeysierra.primal.internal.DefaultManagedProcess;

import java.nio.file.Path;

public final class Primal {

    public static ManagedProcess prepare(Path executable, Object... arguments) {
        Preconditions.checkNotNull(executable, "Executable");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(executable).parameterize(arguments);
    }

    public static ManagedProcess prepare(String command, Object... arguments) {
        Preconditions.checkNotNull(command, "Command");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(command).parameterize(arguments);
    }

    public static ManagedProcess prepare(Path executable, Iterable<?> arguments) {
        Preconditions.checkNotNull(executable, "Executable");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(executable).parameterize(arguments);
    }

    public static ManagedProcess prepare(String command, Iterable<?> arguments) {
        Preconditions.checkNotNull(command, "Command");
        Preconditions.checkNotNull(arguments, "Arguments");
        return new DefaultManagedProcess(command).parameterize(arguments);
    }

}
