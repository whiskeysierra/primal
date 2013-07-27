package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;
import static org.whiskeysierra.process.PathMatchers.exists;

public final class PrimalIntegrationTest {

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

}
