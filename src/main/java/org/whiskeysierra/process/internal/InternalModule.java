package org.whiskeysierra.process.internal;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.Os;
import org.whiskeysierra.process.ProcessService;
import org.whiskeysierra.process.internal.escape.EscapeModule;
import org.whiskeysierra.process.internal.execute.ExecuteModule;
import org.whiskeysierra.process.internal.manage.ManageModule;
import org.whiskeysierra.process.internal.process.ProcessModule;
import org.whiskeysierra.process.internal.redirect.RedirectModule;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

@Module(
    injects = Root.class,
    includes = {
        EscapeModule.class,
        ExecuteModule.class,
        ManageModule.class,
        ProcessModule.class,
        RedirectModule.class
    }
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
    @Singleton
    public ProcessService provideProcessService(DefaultProcessService service) {
        return service;
    }

    @Provides
    @Singleton
    public Executor provideExecutor() {
        return executor;
    }

    @Provides
    @Singleton
    public Os provideOs() {
        return Os.getCurrent();
    }

}
