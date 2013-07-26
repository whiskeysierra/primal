package org.whiskeysierra.primal;

import org.whiskeysierra.primal.Stream.Input;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ManagedProcessUsage {

    public void call() throws IOException {
    }

    public void cwd() throws IOException {
        final ProcessService service = Primal.createService();
        final Path workingDirectory = Paths.get("/path/to/directory");
        final File input = Paths.get("input").toFile();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.in(workingDirectory);
        managed.with("CLICOLOR", "0");
        managed.redirect(Stream.INPUT, Redirect.from(input));
        managed.redirectErrorStream();
        managed.consume(Input.OUTPUT);
        managed.allow(0, 1, 2, 3, 4);
    }

}
