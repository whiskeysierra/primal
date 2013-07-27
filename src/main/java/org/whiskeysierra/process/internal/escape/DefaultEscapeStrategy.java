package org.whiskeysierra.process.internal.escape;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
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
        return executable.toAbsolutePath().toString();
    }

    @Override
    public String escape(String command) {
        return command;
    }

    @Override
    public List<String> escape(Iterable<?> arguments) {
        // TODO null check
        // TODO handle nulls in arguments
        // TODO escape/converting
        // TODO substitution?
        return Lists.transform(Lists.newArrayList(arguments), Functions.toStringFunction());
    }
}
