package org.whiskeysierra.process;

import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface RunningProcess extends Future<Integer>,
    InputSupplier<InputStream>, OutputSupplier<OutputStream> {

    State currentState();

    OutputStream getStandardInput();

    InputStream getStandardOutput();

    InputStream getStandardError();

    InputSupplier<InputStream> getError();

    // TODO sure we don't want the original throws clause here?
    @Override
    Integer get();

    @Override
    Integer get(long timeout, @Nullable TimeUnit unit) throws TimeoutException;

    void await();

}
