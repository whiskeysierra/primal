package org.whiskeysierra.process.internal.escape;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.whiskeysierra.process.OsExamples;
import org.whiskeysierra.process.spi.EscapeStrategy;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public final class DefaultEscapeStrategyEscapeExecutableTest {

    private final Path input;
    private final String expected;

    public DefaultEscapeStrategyEscapeExecutableTest(Path input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> getExamples() {
        return Arrays.asList(new Object[][]{
            {Paths.get("/usr/bin/javac"), "/usr/bin/javac"},
            {Paths.get("/usr/bin/java"), "/usr/bin/java"},
            {Paths.get("/usr/bin/python"), "/usr/bin/python"},
            {Paths.get("/usr", "local", "bin", "node"), "/usr/local/bin/node"},
            {Paths.get("/opt/Adobe Reader/bin/acroread"), "/opt/Adobe Reader/bin/acroread"},
            {Paths.get("/opt/path with more/spaces"), "/opt/path with more/spaces"},
            {Paths.get("C:\\Program Files (x86)\\Evernote\\Evernote\\ENScript.exe"),
                "C:\\Program Files (x86)\\Evernote\\Evernote\\ENScript.exe"},
            {Paths.get("C:\\Program Files\\Evernote\\Evernote\\ENScript.exe"),
                "C:\\Program Files\\Evernote\\Evernote\\ENScript.exe"},
        });
    }

    @Test
    public void test() {   
        final EscapeStrategy unit = new DefaultEscapeStrategy();
        assertThat(unit.escape(input), equalTo(expected));
    }

}
