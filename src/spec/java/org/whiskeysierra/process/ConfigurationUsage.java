package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Paths;

public final class ConfigurationUsage {

    public void configure() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.in(Paths.get("/home"));
        managed.with("CLICOLOR", "0");
        managed.allow(0, 1);

        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.ERROR, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
