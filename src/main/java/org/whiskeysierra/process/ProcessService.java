package org.whiskeysierra.process;

import com.google.common.io.ByteSource;

import java.io.IOException;
import java.nio.file.Path;

public interface ProcessService {

    void call(Path executable, Object... arguments) throws IOException;

    void call(String command, Object... arguments) throws IOException;

    void call(Path executable, Iterable<?> arguments) throws IOException;

    void call(String command, Iterable<?> arguments) throws IOException;

    String toString(Path executable, Object... arguments) throws IOException;

    String toString(String command, Object... arguments) throws IOException;

    String toString(Path executable, Iterable<?> arguments) throws IOException;

    String toString(String command, Iterable<?> arguments) throws IOException;

    ManagedProcess prepare(Path executable, Object... arguments);

    ManagedProcess prepare(String command, Object... arguments);

    ManagedProcess prepare(Path executable, Iterable<?> arguments);

    ManagedProcess prepare(String command, Iterable<?> arguments);

}
