package org.whiskeysierra.primal.internal;

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
