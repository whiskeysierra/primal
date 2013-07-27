package org.whiskeysierra.process.internal;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;

interface InputStreamHandler {

    InputStreamHandler NOOP = new InputStreamHandler() {

        @Override
        public void handle(InputStream stream) {
            // nothing to do here...
        }

    };

    InputStreamHandler GOBBLE = new InputStreamHandler() {

        @Override
        public void handle(InputStream stream) throws IOException {
            ByteStreams.copy(stream, ByteStreams.nullOutputStream());
        }

    };

    void handle(InputStream stream) throws IOException;

}
