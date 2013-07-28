package org.whiskeysierra.process;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Paths.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;
import static org.whiskeysierra.process.PathMatchers.exists;

// TODO run integration tests separately from unit tests
public final class PrimalIntegrationTest {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void readCommand() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final String output = Primal.read("echo", "-n", "Hello World");
        assertThat(output.trim(), equalTo("Hello World"));
    }

    @Test
    public void readExecutable() throws IOException {
        final Path python = get("/usr/bin/python");
        assumeThat(python, exists());

        final String output = Primal.read(python, "-c", "print 'Hello World'");
        assertThat(output.trim(), equalTo("Hello World"));
    }

    @Test
    public void redirectOutputToFile() throws IOException {
        // TODO introduce anntotation and rule for that
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

    @Test
    public void redirectInputFromAndOutputToFile() throws IOException {
        // TODO introduce anntotation and rule for that
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
