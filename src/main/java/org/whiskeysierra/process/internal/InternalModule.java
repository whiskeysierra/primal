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

    @Provides
    @Singleton
    public Processor provideProcessor(Os os, Lazy<UnixProcessor> unix, Lazy<Win9xProcessor> win9x,
        Lazy<WinNTProcessor> nt) {

        if (os.getFamilies().contains(Family.UNIX)) {
            return unix.get();
        } else if (os.getFamilies().contains(Family.WIN9X)) {
            return win9x.get();
        } else if (os.getFamilies().contains(Family.WINDOWS)) {
            return nt.get();
        } else {
            // TODO handle more cases
            throw new UnsupportedOperationException();
        }
    }

    @Provides
    public ProcessFactory provideProcessFactory(DefaultProcessFactory factory) {
        return factory;
    }

}
