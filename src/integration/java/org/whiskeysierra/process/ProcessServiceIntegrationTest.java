package org.whiskeysierra.process;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.whiskeysierra.process.internal.os.Family;
import org.whiskeysierra.process.internal.os.Os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

// TODO make abstract, implement per os family
public final class ProcessServiceIntegrationTest {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

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

    // TODO add "type" test on windows
    @Test
    public void nullRedirection() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.ERROR, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    // TODO add "type" test on windows
    @Test
    public void redirectOutputToFile() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");
        final Path output = temp.newFile().toPath();

        final ManagedProcess managed = service.prepare("cat", input);

        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }

        final byte[] actual = Files.readAllBytes(output);
        final byte[] expected = Files.readAllBytes(input);

        assertThat(actual, equalTo(expected));
    }

    // TODO add "type" test on windows
    @Test
    public void redirectInputFromAndOutputToFile() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");
        final Path output = temp.newFile().toPath();

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }

        final byte[] actual = Files.readAllBytes(output);
        final byte[] expected = Files.readAllBytes(input);

        assertThat(actual, equalTo(expected));
    }

}
