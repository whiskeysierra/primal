package org.whiskeysierra.primal;

import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface RunningProcess extends Future<Integer>, InputSupplier<InputStream>, OutputSupplier<OutputStream> {

    Integer get();

    Integer get(long timeout, @Nullable TimeUnit unit) throws TimeoutException;

    void await();

}
