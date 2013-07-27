package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.RunningProcess;

interface ProcessExecutor {

    RunningProcess execute(ManagedProcess managed);

}
