package org.whiskeysierra.primal;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.primal.Stream.Output;
import org.whiskeysierra.primal.internal.DefaultProcessService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;

// TODO specify that creating a service and reusing it is usually better, compare Pattern.compile(..)
public final class Primal {

    // TODO document that this won't support gobbling and timeouts
    public static ProcessService createService() {
        return new DefaultProcessService();
    }

    public static ProcessService createService(Executor executor) {
        throw new UnsupportedOperationException();
    }

    public static void call(Path executable, Object... arguments) throws IOException {
        final ProcessService service = createService();
        service.call(executable, arguments);
    }

    public static void call(String command, Object... arguments) throws IOException {
        final ProcessService service = createService();
        service.call(command, arguments);
    }

    public static void call(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        service.call(executable, arguments);
    }

    public static void call(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        service.call(command, arguments);
    }

    public static String read(Path executable, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.read(executable, arguments);
    }

    public static String read(String command, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.read(command, arguments);
    }

    public static String read(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.read(executable, arguments);
    }

    public static String read(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.read(command, arguments);
    }

}
