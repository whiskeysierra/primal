package org.whiskeysierra.process.internal;

import javax.inject.Inject;
import java.io.IOException;

final class Win9xProcessor implements Processor {

    @Inject
    Win9xProcessor() {

    }

    @Override
    public Process start() throws IOException {
        throw new UnsupportedOperationException();
    }

}
