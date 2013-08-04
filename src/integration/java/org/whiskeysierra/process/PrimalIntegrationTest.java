package org.whiskeysierra.process;

import org.junit.Test;
import org.whiskeysierra.process.internal.os.Family;
import org.whiskeysierra.process.internal.os.Os;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class PrimalIntegrationTest {

    private final Path unix = Paths.get("src/test/resources/debug/script.sh");
    private final Path windows = Paths.get("src/test/resources/debug/script.bat");

    @Test
    public void callExecutableUnix() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        Primal.call(unix, "Hello", "World");
    }
    @Test
    public void callExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.WINDOWS));

        Primal.call(windows, "Hello", "World");
    }

    @Test
    public void callCommand() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    @Test
    public void readExecutableUnix() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final String output = Primal.toString(unix, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readExecutableWindows() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.WINDOWS));

        final String output = Primal.toString(windows, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readCommand() throws IOException {
        String output = Primal.toString("echo", "Hello", "World");
        assertThat(output, equalTo("Hello World\n"));
    }

}
