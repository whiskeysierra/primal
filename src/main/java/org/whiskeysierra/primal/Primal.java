package org.whiskeysierra.primal;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.primal.internal.DefaultManagedProcess;

import java.io.IOException;
import java.nio.file.Path;

public final class Primal {

    public static void call(Path executable, Object... arguments) throws IOException {
        prepare(executable, arguments).call().await();
    }

    public static void call(String command, Object... arguments) throws IOException {
        prepare(command, arguments).call().await();
    }

    private static String getOutput(ManagedProcess managed) throws IOException {
        final RunningProcess process = managed.call();
        final byte[] output = ByteStreams.toByteArray(process);
        process.await();
        return new String(output, Charsets.UTF_8);
    }

    public static String read(Path executable, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(executable, arguments);
        return getOutput(managed);
    }

    public static String read(String command, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(command, arguments);
        return getOutput(managed);
    }

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
