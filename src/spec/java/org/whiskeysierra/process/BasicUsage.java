package org.whiskeysierra.process;

import java.io.IOException;

public final class BasicUsage {

    public void call() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    public void read() throws IOException {
        String output = Primal.read("echo", "Hello", "World");
        System.out.println(output);
    }

}
