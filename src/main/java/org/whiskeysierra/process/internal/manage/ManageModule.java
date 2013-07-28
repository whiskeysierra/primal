package org.whiskeysierra.process.internal.manage;

import dagger.Module;
import dagger.Provides;
import org.whiskeysierra.process.ManagedProcess;

@Module(library = true, complete = false)
public final class ManageModule {

    @Provides
    public ManagedProcess provideManagedProcess(DefaultManagedProcess process) {
        return process;
    }

}
