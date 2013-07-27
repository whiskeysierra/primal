package org.whiskeysierra.process.spi;

import java.io.IOException;
import java.io.OutputStream;

public interface OutputStreamHandler {

    void handle(OutputStream stream) throws IOException;

}
