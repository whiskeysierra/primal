package org.whiskeysierra.process.internal.os;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(library = true)
public final class OsModule {

    @Provides
    @Singleton
    public Os provideOs() {
        return Os.getCurrent();
    }

}
