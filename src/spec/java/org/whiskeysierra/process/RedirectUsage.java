package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RedirectUsage {

    public void redirectInputFromAndOutputToFile() throws IOException {
        final ProcessService service = Primal.createService();

        final Path input = Paths.get("input");
        final Path output = Paths.get("output");

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
