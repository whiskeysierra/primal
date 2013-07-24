package org.whiskeysierra.primal;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Usage {

    public static void test() throws IOException {
        final File input = new File("input");
        final File output = new File("output");

        final Path python = Paths.get("/usr/bin/python");

        final ManagedProcess managed = Primal.prepare(python);
        managed.parameterize("-c", "print 'Hello from Python'");
        final RunningProcess process = managed.call();

        // write to stdin
        Files.copy(input, process);
        // read from stdout
        Files.copy(process, output);

        process.await();
    }

}
