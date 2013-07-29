package org.whiskeysierra.process;

import com.google.common.io.Files;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class GuavaProcessIoUsage {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void test() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final File input = Paths.get("src/test/resources/lorem-ipsum.txt").toFile();
        final File output = temp.newFile();

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input.toPath()));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            Files.copy(process, output);
            process.await();
        }
    }

}
