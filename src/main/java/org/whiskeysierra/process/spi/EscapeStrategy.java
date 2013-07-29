package org.whiskeysierra.process.spi;

import java.nio.file.Path;
import java.util.List;

public interface EscapeStrategy {

    String escape(Path executable);

    String escape(String command);

    List<String> escape(Iterable<?> arguments);

}
