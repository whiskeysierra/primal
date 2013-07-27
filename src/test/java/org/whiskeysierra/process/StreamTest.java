package org.whiskeysierra.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public final class StreamTest {

    private final String expected;
    private final Stream input;

    public StreamTest(String expected, Stream input) {
        this.expected = expected;
        this.input = input;
    }

    @Parameters
    public static Collection<Object[]> getExamples() {
        return Arrays.asList(new Object[][] {
            {"stdin", Stream.INPUT},
            {"stdout", Stream.OUTPUT},
            {"stderr", Stream.ERROR},
        });
    }

    @Test
    public void testToString() {
        assertThat(input.toString(), equalTo(expected));
    }

}
