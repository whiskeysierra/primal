package org.whiskeysierra.primal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface ManagedProcess {

    ManagedProcess parameterize(Object... arguments);

    ManagedProcess parameterize(Iterable<?> arguments);

    ManagedProcess in(Path directory);

    ManagedProcess with(String variable, String value);

    ManagedProcess with(Map<String, String> properties);

    RunningProcess call() throws IOException;
}
