package org.whiskeysierra.process;

import com.google.common.io.ByteSource;

import java.io.IOException;

public final class BasicUsage {

    public void callUsage() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    public void toStringUsage() throws IOException {
        String output = Primal.toString("echo", "Hello", "World");
        System.out.println(output);
    }

    public void readUsage() throws IOException {
        final ByteSource output = Primal.read("echo", "Hello", "World");

        // process output further
        final byte[] bytes = output.read();
    }

}
