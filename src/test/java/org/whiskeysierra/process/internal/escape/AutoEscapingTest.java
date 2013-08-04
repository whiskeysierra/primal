package org.whiskeysierra.process.internal.escape;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.whiskeysierra.process.internal.os.Family;
import org.whiskeysierra.process.internal.os.Os;
import org.whiskeysierra.process.Primal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

// TODO add windows test
public final class AutoEscapingTest {

    @Test
    public void unix() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final Path executable = Paths.get("src/test/resources/debug/script.sh");
        final Path path = Paths.get("path with spaces", "and sub directories");
        final Object[] arguments = {"a", "b", "c d e", path};
        final String output = Primal.toString(executable, arguments);

        final String expected = Joiner.on('\n').join(arguments) + '\n';
        assertThat(output, equalTo(expected));
    }

}
