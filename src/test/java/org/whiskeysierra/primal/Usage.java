package org.whiskeysierra.primal;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Usage {

    public void call() throws IOException {
        Primal.call("ls", "-lh");
    }

    public void read() throws IOException {
        String output = Primal.read("ls", "-lh");
        // process output further
    }

    public void cwd() throws IOException {
        final Path path = Paths.get("/path/to/directory");
        Primal.prepare("ls", "-lh").in(path).call().await();
    }

    public void complete() throws IOException {
        final File input = Paths.get("input").toFile();
        final File output = Paths.get("output").toFile();

        final Path python = Paths.get("/usr/bin/python");

        final ManagedProcess managed = Primal.prepare(python);
        managed.parameterize("-c", "print 'Hello from Python'");
        final RunningProcess process = managed.call();

        // read from stdin and write to stdout
        Files.copy(input, process);
        Files.copy(process, output);

        process.await();
    }

}
