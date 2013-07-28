package org.whiskeysierra.process.internal.execute;

import org.whiskeysierra.process.spi.OutputStreamHandler;

import java.io.IOException;
import java.io.OutputStream;

final class OutputStreamHandlers {

    static final OutputStreamHandler NOOP = new OutputStreamHandler() {

        @Override
        public void handle(OutputStream stream) {
            // nothing to do here...
        }

    };

    static final OutputStreamHandler CLOSE = new OutputStreamHandler() {

        @Override
        public void handle(OutputStream stream) throws IOException {
            stream.close();
        }

    };

    private OutputStreamHandlers() {

    }

}
