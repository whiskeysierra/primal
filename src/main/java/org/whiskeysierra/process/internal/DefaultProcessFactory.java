package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.RunningProcess;

import javax.inject.Inject;

final class DefaultProcessFactory implements ProcessFactory {

    @Inject
    DefaultProcessFactory(Processor processor) {

    }

    @Override
    public RunningProcess createFrom(ManagedProcess managed) {
        throw new UnsupportedOperationException();
    }

}
