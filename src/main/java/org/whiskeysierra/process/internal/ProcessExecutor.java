package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.RunningProcess;

import java.io.IOException;

interface ProcessExecutor {

    RunningProcess execute(AccessibleManagedProcess managed) throws IOException;

}
