package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.RunningProcess;

interface ProcessExecutor {

    RunningProcess execute(AccessibleManagedProcess managed);

}
