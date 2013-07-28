package org.whiskeysierra.process.internal.escape;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.spi.EscapeStrategy;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public final class EscapeModule {

    @Provides
    @Singleton
    public EscapeStrategy provideEscapeStrategy(DefaultEscapeStrategy strategy) {
        return strategy;
    }

}
