package org.whiskeysierra.primal;

import org.whiskeysierra.primal.Stream.Output;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.Map;

// TODO specify IOException as exception of choice?!
public interface ManagedProcess {

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Object... arguments);

    // TODO specify defensive-copy?
    ManagedProcess parameterize(Iterable<?> arguments);

    ManagedProcess in(Path directory);

    ManagedProcess with(String variable, String value);

    ManagedProcess with(Map<String, String> properties);

    ManagedProcess redirect(Stream stream, Redirect redirect);

    ManagedProcess redirectErrorStream();

    ManagedProcess noInput();

    ManagedProcess consume(Output output);

    ManagedProcess consume(Output... outputs);

    ManagedProcess consume(Iterable<Output> inputs);

    ManagedProcess allow(int exitValue);

    // TODO specify whether defensive copy or not
    ManagedProcess allow(int... exitValues);

    RunningProcess call() throws IOException;

}
