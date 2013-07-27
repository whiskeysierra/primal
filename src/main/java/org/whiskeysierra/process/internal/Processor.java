package org.whiskeysierra.process.internal;

import java.io.IOException;

public interface Processor {

    Process start() throws IOException;

}
