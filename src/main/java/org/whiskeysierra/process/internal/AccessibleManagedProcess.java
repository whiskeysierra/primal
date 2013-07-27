package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

interface AccessibleManagedProcess {


    Path getExecutable();

    String getCommand();

    List<Object> getArguments();

    Path getDirectory();

    Map<String, String> getEnvironment();

    Redirection getRedirection(Stream stream);

    int[] getAllowedExitValues();
}
