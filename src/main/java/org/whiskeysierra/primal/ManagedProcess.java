package org.whiskeysierra.primal;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.Map;

public interface ManagedProcess {

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Object... arguments);

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Iterable<?> arguments);

    ManagedProcess in(Path directory);

    ManagedProcess with(String variable, String value);

    ManagedProcess with(Map<String, String> properties);

    ManagedProcess redirect(Stream stream, Redirect redirect);

    ManagedProcess gobble(Stream stream);

    ManagedProcess allow(int exitValue);

    ManagedProcess allow(int... exitValues);

    RunningProcess call() throws IOException;
}
