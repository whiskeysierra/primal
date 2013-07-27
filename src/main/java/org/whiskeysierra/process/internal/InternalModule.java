package org.whiskeysierra.process.internal;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.Family;
import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.Os;
import org.whiskeysierra.process.ProcessService;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

@Module(injects = Root.class)
public final class InternalModule {

    private final Executor executor;

    public InternalModule() {
        this.executor = null;
    }

    public InternalModule(Executor executor) {
        this.executor = executor;
    }

    @Provides
    @Singleton
    public Executor provideExecutor() {
        return executor;
    }

    @Provides
    @Singleton
    public ProcessService provideProcessService(DefaultProcessService service) {
        return service;
    }

    @Provides
    public ManagedProcess provideManagedProcess(DefaultManagedProcess process) {
        return process;
    }

    @Provides
    @Singleton
    public Redirector provideRedirector(DefaultRedirector redirector) {
        return redirector;
    }

    @Provides
    @Singleton
    public Os provideOs() {
        return Os.getCurrent();
    }

}
