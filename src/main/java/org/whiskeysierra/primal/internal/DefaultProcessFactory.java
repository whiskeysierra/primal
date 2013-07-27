package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.RunningProcess;

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
