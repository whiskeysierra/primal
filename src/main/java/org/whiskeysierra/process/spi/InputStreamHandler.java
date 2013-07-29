package org.whiskeysierra.process.spi;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamHandler {

    void handle(InputStream stream) throws IOException;

}
