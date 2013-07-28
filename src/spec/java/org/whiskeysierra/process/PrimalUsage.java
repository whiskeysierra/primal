package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class PrimalUsage {

    private final Path executable = Paths.get("src/test/resources/debug/script.sh");

    @Test
    public void callExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        Primal.call(executable, "Hello", "World");
    }

    @Test
    public void callCommand() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    @Test
    public void readExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final String output = Primal.read(executable, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readCommand() throws IOException {
        String output = Primal.read("echo", "Hello", "World");
        assertThat(output, equalTo("Hello World\n"));
    }

}
