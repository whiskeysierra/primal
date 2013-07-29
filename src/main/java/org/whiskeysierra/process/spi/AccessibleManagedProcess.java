package org.whiskeysierra.process.spi;

import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;

import java.nio.file.Path;
import java.util.Map;

public interface AccessibleManagedProcess {


    Path getExecutable();

    String getCommand();

    Iterable<?> getArguments();

    Path getDirectory();

    Map<String, String> getEnvironment();

    Redirection getRedirection(Stream stream);

    int[] getAllowedExitValues();
}
