package org.whiskeysierra.process;

import com.google.common.io.ByteSource;
import dagger.ObjectGraph;
import org.whiskeysierra.process.internal.InternalModule;
import org.whiskeysierra.process.internal.Root;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;

// TODO specify that creating a service and reusing it is usually better, compare Pattern.compile(..)
public final class Primal {

    // TODO document that this won't support gobbling and timeouts
    public static ProcessService createService() {
        return ObjectGraph.create(new InternalModule()).get(Root.class).getService();
    }

    public static ProcessService createService(Executor executor) {
        return ObjectGraph.create(new InternalModule(executor)).get(Root.class).getService();
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

    public static String toString(Path executable, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.toString(executable, arguments);
    }

    public static String toString(String command, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.toString(command, arguments);
    }

    public static String toString(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.toString(executable, arguments);
    }

    public static String toString(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.toString(command, arguments);
    }

    public static ByteSource read(Path executable, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.prepare(executable, arguments).read();
    }

    public static ByteSource read(String command, Object... arguments) throws IOException {
        final ProcessService service = createService();
        return service.prepare(command, arguments).read();
    }

    public static ByteSource read(Path executable, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.prepare(executable, arguments).read();
    }

    public static ByteSource read(String command, Iterable<?> arguments) throws IOException {
        final ProcessService service = createService();
        return service.prepare(command, arguments).read();
    }


}
