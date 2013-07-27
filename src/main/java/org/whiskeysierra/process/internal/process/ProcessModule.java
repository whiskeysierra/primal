package org.whiskeysierra.process.internal.process;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.spi.ProcessFactory;

import javax.inject.Singleton;

@Module(library = true)
public final class ProcessModule {

    @Provides
    @Singleton
    public ProcessFactory provideProcessFactory(DefaultProcessFactory factory) {
        return factory;
    }

}
