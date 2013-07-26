package org.whiskeysierra.primal.internal;

import com.google.common.io.InputSupplier;

import java.io.IOException;
import java.io.InputStream;

final class ErrorStreamSupplier implements InputSupplier<InputStream> {

    private final Process process;

    public ErrorStreamSupplier(Process process) {
        this.process = process;
    }

    @Override
    public InputStream getInput() throws IOException {
        return process.getErrorStream();
    }

}
