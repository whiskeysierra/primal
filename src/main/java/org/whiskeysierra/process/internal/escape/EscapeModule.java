package org.whiskeysierra.process.internal.escape;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.spi.EscapeStrategy;

import javax.inject.Singleton;

@Module(library = true)
public final class EscapeModule {

    // TODO @Default?
    @Provides
    @Singleton
    public EscapeStrategy provideEscapeStrategy(DefaultEscapeStrategy strategy) {
        return strategy;
    }

}
