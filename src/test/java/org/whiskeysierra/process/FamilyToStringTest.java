package org.whiskeysierra.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.whiskeysierra.process.internal.os.Family;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public final class FamilyToStringTest {

    private final String expected;
    private final Family input;

    public FamilyToStringTest(String expected, Family input) {
        this.expected = expected;
        this.input = input;
    }

    @Parameters
    public static Collection<Object[]> getExamples() {
        return Arrays.asList(new Object[][] {
            {"mac", Family.MAC},
            {"netware", Family.NETWARE},
            {"openvms", Family.OPENVMS},
            {"os/2", Family.OS_2},
            {"os/400", Family.OS_400},
            {"unix", Family.UNIX},
            {"win9x", Family.WIN9X},
            {"windows", Family.WINDOWS},
            {"z/os", Family.Z_OS},
        });
    }

    @Test
    public void testToString() {
        assertThat(input.toString(), equalTo(expected));
    }

}
