package org.whiskeysierra.primal;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.primal.Stream.Output;
import org.whiskeysierra.primal.internal.DefaultProcessService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;

// TODO no singleton
// TODO should all delegate to ProcessService
// TODO specify that creating a service and reusing it is usually better, compare Pattern.compile(..)
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

    private static void callAndAwait(ManagedProcess managed) throws IOException {
        managed.noInput();
        managed.consume(Stream.OUTPUT);
        managed.consume(Stream.ERROR);
        managed.call().await();
    }

    public static void call(Path executable, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(executable, arguments);
        callAndAwait(managed);
    }

    public static void call(String command, Object... arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(command, arguments);
        callAndAwait(managed);
    }

    public static void call(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(executable, arguments);
        callAndAwait(managed);
    }

    public static void call(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = SINGLETON;
        final ManagedProcess managed = service.prepare(command, arguments);
        callAndAwait(managed);
    }

    private static String callAndCaptureOutput(ManagedProcess managed) throws IOException {
        managed.noInput();
        managed.consume(Output.ERROR);

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
