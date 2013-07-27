package org.whiskeysierra.process;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class GuavaProcessIoUsage {

    public void complete() throws IOException {
        final ProcessService service = Primal.createService();

        final File input = Paths.get("input").toFile();
        final File output = Paths.get("output").toFile();
        final File error = Paths.get("error").toFile();

        final Path python = Paths.get("/usr/bin/python");

        final ManagedProcess managed = service.prepare(python);
        managed.parameterize("-c", "print 'Hello from Python'");
        final RunningProcess process = managed.call();

        // write to stdin
        Files.copy(input, process);

        // read from stdout
        Files.copy(process, output);

        // and stderr
        Files.copy(process.getError(), error);

        process.await();
    }

}
