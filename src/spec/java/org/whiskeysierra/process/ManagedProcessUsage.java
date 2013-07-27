package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.whiskeysierra.process.Redirection.appendTo;
import static org.whiskeysierra.process.Redirection.from;
import static org.whiskeysierra.process.Redirection.to;

public final class ManagedProcessUsage {

    public void cwd() throws IOException {
        final ProcessService service = Primal.createService();
        final Path workingDirectory = Paths.get("/path/to/directory");
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.in(workingDirectory);

        managed.call().await();
    }

    public void env() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.with("CLICOLOR", "0");

        managed.call().await();
    }

    public void exitValues() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        managed.call().await();
    }

    public void nullRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        // no stdin
        managed.redirect(Stream.INPUT, Redirection.NULL);
        // redirect stderr into stdout
        managed.redirect(Stream.ERROR, to(Stream.OUTPUT));
        // redirect stdout to /dev/null (or similar)
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        managed.call().await();
    }

    public void fileRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        final Path input = Paths.get("stdin.txt");
        final Path error = Paths.get("stderr.log");
        final Path output = Paths.get("stdout.log");

        // read stdin from stdin.txt
        managed.redirect(Stream.INPUT, from(input));
        // redirect to stderr.log (overwrite)
        managed.redirect(Stream.ERROR, to(error));
        // append to stdout.log
        managed.redirect(Stream.OUTPUT, appendTo(output));

        managed.call().await();
    }

}
