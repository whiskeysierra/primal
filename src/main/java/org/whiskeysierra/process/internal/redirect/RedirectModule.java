package org.whiskeysierra.process.internal.redirect;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;
import org.whiskeysierra.process.internal.Default;
import org.whiskeysierra.process.spi.Redirector;

import javax.inject.Singleton;
import java.util.Map;

@Module(library = true, complete = false)
public final class RedirectModule {

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

}
