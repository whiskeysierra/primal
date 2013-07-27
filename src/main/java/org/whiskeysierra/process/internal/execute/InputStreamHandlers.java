package org.whiskeysierra.process.internal.execute;

import com.google.common.io.ByteStreams;
import org.whiskeysierra.process.spi.InputStreamHandler;

import java.io.IOException;
import java.io.InputStream;

final class InputStreamHandlers {

    public static final InputStreamHandler NOOP = new InputStreamHandler() {

        @Override
        public void handle(InputStream stream) {
            // nothing to do here...
        }

    };

    public static final InputStreamHandler GOBBLE = new InputStreamHandler() {

        @Override
        public void handle(InputStream stream) throws IOException {
            ByteStreams.copy(stream, ByteStreams.nullOutputStream());
        }

    };

    private InputStreamHandlers() {

    }

}
