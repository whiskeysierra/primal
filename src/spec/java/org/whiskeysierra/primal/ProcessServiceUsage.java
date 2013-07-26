package org.whiskeysierra.primal;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ProcessServiceUsage {

    public void complete() throws IOException {
        final ProcessService service = Primal.createService();

        final File input = Paths.get("input").toFile();
        final File output = Paths.get("output").toFile();

        final Path python = Paths.get("/usr/bin/python");

        final ManagedProcess managed = service.prepare(python);
        managed.parameterize("-c", "print 'Hello from Python'");
        final RunningProcess process = managed.call();

        // read from stdin and write to stdout
        Files.copy(input, process);
        Files.copy(process, output);

        process.await();
    }

}
