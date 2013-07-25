package org.whiskeysierra.primal;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.primal.internal.DefaultProcessService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;

public final class Primal {

    // TODO support gobbling?
    private static final ProcessService SINGLETON = createService();

    // TODO document that this won't support gobbling and timeouts
    public static ProcessService createService() {
        return new DefaultProcessService();
    }

    public static ProcessService createService(Executor executor) {
        throw new UnsupportedOperationException();
    }

    public static void call(Path executable, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        service.prepare(executable, arguments).call().await();
    }

    public static void call(String command, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        service.prepare(command, arguments).call().await();
    }

    public static void call(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        service.prepare(executable, arguments).call().await();
    }

    public static void call(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        service.prepare(command, arguments).call().await();
    }

    private static String callAndCaptureOutput(ManagedProcess managed) throws IOException {
        final RunningProcess process = managed.call();
        // TODO handle possible exception?!
        final byte[] output = ByteStreams.toByteArray(process);
        process.await();
        return new String(output, Charsets.UTF_8);
    }

    public static String read(Path executable, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(executable, arguments);
        return callAndCaptureOutput(managed);
    }

    public static String read(String command, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(command, arguments);
        return callAndCaptureOutput(managed);
    }

    public static String read(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(executable, arguments);
        return callAndCaptureOutput(managed);
    }

    public static String read(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(command, arguments);
        return callAndCaptureOutput(managed);
    }

}