package org.whiskeysierra.process.internal;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.Os;
import org.whiskeysierra.process.ProcessService;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.Executor;

@Module(injects = Root.class)
public final class InternalModule {

    // TODO in case we only have one stream gobbler, we might just use the current thread and
    // get a away without one
    private final Executor executor;

    public InternalModule() {
        this.executor = null;
    }

    public InternalModule(Executor executor) {
        this.executor = executor;
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
    @Default
    public Map<Stream, Redirection> provideDefaultRedirections(Redirector redirector) {
        return redirector.getDefaults();
    }

    @Provides
    @Singleton
    public Executor provideExecutor() {
        return executor;
    }

    @Provides
    @Singleton
    public ProcessExecutor provideProcessExecutor(DefaultProcessExecutor executor) {
        return executor;
    }

    @Provides
    @Singleton
    public Os provideOs() {
        return Os.getCurrent();
    }

}
