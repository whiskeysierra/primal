package org.whiskeysierra.primal.internal;

import javax.inject.Inject;
import java.io.IOException;

final class WinNTProcessor implements Processor {

    @Inject
    WinNTProcessor() {

    }

    @Override
    public Process start() throws IOException {
        throw new UnsupportedOperationException();
    }

}
