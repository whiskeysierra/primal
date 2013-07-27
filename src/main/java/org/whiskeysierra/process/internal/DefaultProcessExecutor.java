package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.RunningProcess;

import javax.inject.Inject;
import java.util.concurrent.Executor;

final class DefaultProcessExecutor implements ProcessExecutor {

    private final Executor executor;

    @Inject
    DefaultProcessExecutor(Executor executor) {
        this.executor = executor;
    }


    @Override
    public RunningProcess execute(ManagedProcess managed) {
        throw new UnsupportedOperationException();
    }

}
