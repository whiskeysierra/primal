package org.whiskeysierra.process.internal;

import javax.inject.Inject;
import java.io.IOException;

class UnixProcessor implements Processor {

    @Inject
    UnixProcessor() {

    }

    @Override
    public Process start() throws IOException {
        throw new UnsupportedOperationException();
    }

}
