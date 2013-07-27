package org.whiskeysierra.process.internal.escape;

import org.whiskeysierra.process.spi.EscapeStrategy;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;

final class DefaultEscapeStrategy implements EscapeStrategy {

    @Inject
    DefaultEscapeStrategy() {

    }

    @Override
    public String escape(Path executable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String escape(String command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> escape(Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }
}
