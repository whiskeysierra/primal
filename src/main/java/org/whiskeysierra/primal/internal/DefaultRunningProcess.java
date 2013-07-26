package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.RunningProcess;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

final class DefaultRunningProcess implements RunningProcess {

    private final Process process;
    private AtomicReference<State> state = new AtomicReference<>(State.NEW);

    public DefaultRunningProcess(ProcessBuilder builder) throws IOException {
        this.state.compareAndSet(State.NEW, State.STARTING);
        this.process = builder.start();
        this.state.compareAndSet(State.STARTING, State.RUNNING);
    }

    @Override
    public InputStream getInput() {
        return process.getInputStream();
    }

    @Override
    public OutputStream getOutput() {
        return process.getOutputStream();
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
        return state.get() == State.CANCELLED;
    }

    @Override
    public boolean isDone() {
        return state.get() == State.DONE;
    }

    @Override
    public Integer get() {
        try {
            final int exitValue = process.waitFor();

            // TODO support configurable exit values
            if (exitValue == 0) {
                state.compareAndSet(State.RUNNING, State.DONE);
                return 0;
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

}
