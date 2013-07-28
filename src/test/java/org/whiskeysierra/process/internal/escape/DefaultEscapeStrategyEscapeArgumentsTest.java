package org.whiskeysierra.process.internal.escape;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.whiskeysierra.process.spi.EscapeStrategy;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public final class DefaultEscapeStrategyEscapeArgumentsTest {

    private final Iterable<?> input;
    private final List<String> expected;

    public DefaultEscapeStrategyEscapeArgumentsTest(Iterable<?> input, List<String> expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> getExamples() {
        return Arrays.asList(new Object[][]{
            // single path
            {Paths.get("/usr/bin/javac"), Arrays.asList("/usr/bin/javac")},
            {Paths.get("/usr/bin/java"), Arrays.asList("/usr/bin/java")},
            {Paths.get("/usr/bin/python"), Arrays.asList("/usr/bin/python")},
            {Paths.get("/usr", "local", "bin", "node"), Arrays.asList("/usr/local/bin/node")},
            {Paths.get("/opt/Adobe Reader/bin/acroread"), Arrays.asList("/opt/Adobe Reader/bin/acroread")},
            {Paths.get("/opt/path with more/spaces"), Arrays.asList("/opt/path with more/spaces")},
            {Paths.get("C:\\Program Files (x86)\\Evernote\\Evernote\\ENScript.exe"),
                Arrays.asList("C:\\Program Files (x86)\\Evernote\\Evernote\\ENScript.exe")},
            {Paths.get("C:\\Program Files\\Evernote\\Evernote\\ENScript.exe"),
                Arrays.asList("C:\\Program Files\\Evernote\\Evernote\\ENScript.exe")},

            // multiple args
            {
                Arrays.asList(true, false, 100, null, "test", TimeUnit.DAYS, 1.0f),
                Arrays.asList("true", "false", "100", "null", "test", "DAYS", "1.0")
            }
        });
    }

    @Test
    public void test() {   
        final EscapeStrategy unit = new DefaultEscapeStrategy();
        assertThat(unit.escape(input), equalTo(expected));
    }

}
