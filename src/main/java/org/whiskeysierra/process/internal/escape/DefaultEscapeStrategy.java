package org.whiskeysierra.process.internal.escape;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.whiskeysierra.process.spi.EscapeStrategy;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

final class DefaultEscapeStrategy implements EscapeStrategy {

    @Inject
    DefaultEscapeStrategy() {

    }

    @Override
    public String escape(Path executable) {
        return executable.toString();
    }

    @Override
    public String escape(String command) {
        return command;
    }

    @Override
    public List<String> escape(Iterable<?> arguments) {
        // TODO this should be handled more nicely
        if (arguments instanceof Path) {
            // TODO don't overwrite parameter
            arguments = Collections.singleton(arguments);
        }

        // TODO check for file arguments

        // TODO null check
        // TODO handle nulls in arguments
        // TODO escape/converting
        // TODO substitution?

        return Lists.newArrayList(Iterables.transform(arguments, Functions.toStringFunction()));
    }

}
