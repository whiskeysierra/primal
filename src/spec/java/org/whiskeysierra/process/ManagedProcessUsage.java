package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;
import static org.whiskeysierra.process.Redirection.appendTo;
import static org.whiskeysierra.process.Redirection.from;
import static org.whiskeysierra.process.Redirection.to;

public final class ManagedProcessUsage {

    @Test
    public void cwd() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final Path directory = Paths.get("/home");
        final ManagedProcess managed = service.prepare("ls", "-lh").in(directory);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void env() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh", "/home");

        managed.with("CLICOLOR", "0");

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void exitValues() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void nullRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        // no stdin
        managed.redirect(Stream.INPUT, Redirection.NULL);
        // redirect stderr into stdout
        managed.redirect(Stream.ERROR, to(Stream.OUTPUT));
        // redirect stdout to /dev/null (or similar)
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
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

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
