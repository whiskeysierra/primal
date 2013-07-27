package org.whiskeysierra.primal.internal;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.ProcessService;

import java.util.concurrent.Executor;

@Module(
    injects = Root.class
)
public final class InternalModule {

    private final Executor executor;

    public InternalModule() {
        this.executor = null;
    }

    public InternalModule(Executor executor) {
        this.executor = executor;
    }

    @Provides
    public Executor provideExecutor() {
        return executor;
    }

    @Provides
    public ProcessService provideProcessService(DefaultProcessService service) {
        return service;
    }

    @Provides
    public ManagedProcess provideManagedProcess(DefaultManagedProcess process) {
        return process;
    }

}
