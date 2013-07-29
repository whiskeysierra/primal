package org.whiskeysierra.process;

import com.google.common.io.ByteSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

// TODO specify IOException as exception of choice?!
public interface ManagedProcess {

    // TODO find better name
    ManagedProcess setExecutable(Path executable);

    // TODO find better name
    ManagedProcess setCommand(String command);

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Object... arguments);

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Iterable<?> arguments);

    ManagedProcess in(Path directory);

    ManagedProcess with(String variable, String value);

    ManagedProcess with(Map<String, String> properties);

    // TODO IAE on illegal combinations (input -> redirect to, output -> redirect from, output -> stderr)
    ManagedProcess redirect(Stream stream, Redirection redirect);

    ManagedProcess allow(int exitValue);

    // TODO specify whether defensive copy or not
    ManagedProcess allow(int... exitValues);

    RunningProcess call() throws IOException;

    ByteSource read() throws IOException;

}
