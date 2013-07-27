package org.whiskeysierra.process.internal;

import java.io.IOException;
import java.io.OutputStream;

interface OutputStreamHandler {

    OutputStreamHandler NOOP = new OutputStreamHandler() {

        @Override
        public void handle(OutputStream stream) {
            // nothing to do here...
        }

    };

    OutputStreamHandler CLOSE = new OutputStreamHandler() {

        @Override
        public void handle(OutputStream stream) throws IOException {
            stream.close();
        }

    };

    void handle(OutputStream stream) throws IOException;

}
