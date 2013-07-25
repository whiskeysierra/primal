package org.whiskeysierra.primal;

import java.nio.file.Path;

public interface ProcessService {

    ManagedProcess prepare(Path executable, Object... arguments);

    ManagedProcess prepare(String command, Object... arguments);

    ManagedProcess prepare(Path executable, Iterable<?> arguments);

    ManagedProcess prepare(String command, Iterable<?> arguments);

}
