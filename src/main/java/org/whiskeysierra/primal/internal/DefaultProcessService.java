package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.ProcessService;

import java.nio.file.Path;

public final class DefaultProcessService implements ProcessService {

    @Override
    public ManagedProcess prepare(Path executable, Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess prepare(String command, Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess prepare(Path executable, Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ManagedProcess prepare(String command, Iterable<?> arguments) {
        throw new UnsupportedOperationException();
    }

}
