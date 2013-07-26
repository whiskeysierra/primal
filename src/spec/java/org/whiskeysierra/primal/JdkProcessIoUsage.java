package org.whiskeysierra.primal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class JdkProcessIoUsage {

    public void complete() throws IOException {
        final ProcessService service = Primal.createService();

        final Path input = Paths.get("input");
        final Path output = Paths.get("output");
        final Path error = Paths.get("error");

        final Path python = Paths.get("/usr/bin/python");

        final ManagedProcess managed = service.prepare(python);
        managed.parameterize("-c", "print 'Hello from Python'");
        final RunningProcess process = managed.call();

        // write to stdin
        Files.copy(input, process.getStandardInput());

        // read from stdout
        Files.copy(process.getStandardOutput(), output);

        // and stderr
        Files.copy(process.getStandardError(), error);

        process.await();
    }

}
