package org.whiskeysierra.process.internal.process;

import com.google.common.io.InputSupplier;
import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.State;
import org.whiskeysierra.process.internal.Exceptions;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

final class DefaultRunningProcess implements RunningProcess {

    private final Process process;
    private final int[] allowedExitValues;
    private final AtomicReference<State> state = new AtomicReference<>(State.RUNNING);

    public DefaultRunningProcess(Process process, int[] allowedExitValues) {
        this.process = process;

        Arrays.sort(allowedExitValues);
        this.allowedExitValues = allowedExitValues;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning && state.compareAndSet(State.RUNNING, State.CANCELLING)) {
            process.destroy();
            state.compareAndSet(State.CANCELLING, State.CANCELLED);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCancelled() {
        return currentState() == State.CANCELLED;
    }

    @Override
    public boolean isDone() {
        return currentState() == State.DONE;
    }

    @Override
    public State currentState() {
        return state.get();
    }

    @Override
    public OutputStream getStandardInput() {
        return process.getOutputStream();
    }

    @Override
    public InputStream getStandardOutput() {
        return process.getInputStream();
    }

    @Override
    public InputStream getStandardError() {
        return process.getErrorStream();
    }

    @Override
    public OutputStream getOutput() throws IOException {
        return process.getOutputStream();
    }

    @Override
    public InputStream getInput() throws IOException {
        return process.getInputStream();
    }

    @Override
    public InputSupplier<InputStream> getError() {
        return new ErrorStreamSupplier(process);
    }

    private boolean isAllowed(int exitValue) {
        return Arrays.binarySearch(allowedExitValues, exitValue) >= 0;
    }

    @Override
    public Integer get() {
        try {
            final int exitValue = process.waitFor();

            if (isAllowed(exitValue)) {
                state.compareAndSet(State.RUNNING, State.DONE);
                return exitValue;
            } else {
                state.compareAndSet(State.RUNNING, State.FAILED);
                // TODO better exception
                throw new ExecutionException(new IOException("Exit value was " + exitValue));
            }
        } catch (InterruptedException e) {
            cancel(true);
            throw Exceptions.sneakyThrow(e);
        } catch (Exception e) {
            state.set(State.FAILED);
            throw Exceptions.sneakyThrow(e);
        } finally {
            process.destroy();
            Thread.interrupted();
        }
    }

    @Override
    public Integer get(long timeout, @Nullable TimeUnit unit) {
        // TODO implement timeout handling
        return get();
    }

    @Override
    public void await() {
        get();
    }

    static final class ErrorStreamSupplier implements InputSupplier<InputStream> {

        private final Process process;

        public ErrorStreamSupplier(Process process) {
            this.process = process;
        }

        @Override
        public InputStream getInput() throws IOException {
            return process.getErrorStream();
        }

    }

}
