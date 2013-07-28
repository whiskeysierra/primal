package org.whiskeysierra.process.spi;

import org.whiskeysierra.process.RunningProcess;

import java.io.IOException;

public interface ProcessExecutor {

    RunningProcess execute(AccessibleManagedProcess managed) throws IOException;

}
