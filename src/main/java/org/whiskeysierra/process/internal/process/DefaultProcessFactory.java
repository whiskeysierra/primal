package org.whiskeysierra.process.internal.process;

import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.spi.ProcessFactory;

import javax.inject.Inject;

final class DefaultProcessFactory implements ProcessFactory {

    @Inject
    DefaultProcessFactory() {

    }

    @Override
    public RunningProcess create(Process process, int[] allowedExitValues) {
        return new DefaultRunningProcess(process, allowedExitValues);
    }

}
