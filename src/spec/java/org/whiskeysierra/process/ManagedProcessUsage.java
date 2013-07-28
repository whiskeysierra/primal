package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

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
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
