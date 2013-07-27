package org.whiskeysierra.process.spi;

import org.whiskeysierra.process.RunningProcess;

public interface ProcessFactory {

    RunningProcess create(Process process, int[] allowedExitValues);

}
