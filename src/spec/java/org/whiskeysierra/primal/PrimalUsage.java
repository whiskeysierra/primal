package org.whiskeysierra.primal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PrimalUsage {

    public void call() throws IOException {
        Primal.call("ls", "-lh");
    }

    public void read() throws IOException {
        String output = Primal.read("ls", "-lh");
        // process output further
    }

    public void cwd() throws IOException {
        final ProcessService service = Primal.createService();
        final Path path = Paths.get("/path/to/directory");
        service.prepare("ls", "-lh").in(path).call().await();
    }

}
