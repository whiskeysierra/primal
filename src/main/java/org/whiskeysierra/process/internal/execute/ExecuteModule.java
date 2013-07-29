package org.whiskeysierra.process.internal.execute;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.spi.ProcessExecutor;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public final class ExecuteModule {

    @Provides
    @Singleton
    public ProcessExecutor provideProcessExecutor(DefaultProcessExecutor executor) {
        return executor;
    }

}
