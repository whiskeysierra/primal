package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;

interface ProcessFactory {

    RunningProcess createFrom(ManagedProcess managed);

}
