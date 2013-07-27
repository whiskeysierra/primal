package org.whiskeysierra.primal.internal;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.primal.ManagedProcess;
import org.whiskeysierra.primal.ProcessService;
import org.whiskeysierra.primal.RunningProcess;
import org.whiskeysierra.primal.Stream;
import org.whiskeysierra.primal.Stream.Output;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;

final class DefaultProcessService implements ProcessService {

    private final Provider<ManagedProcess> provider;

    @Inject
    DefaultProcessService(Provider<ManagedProcess> provider) {
        this.provider = provider;
    }

    private void callAndAwait(ManagedProcess managed) throws IOException {
        managed.noInput();
        managed.consume(Stream.OUTPUT);
        managed.consume(Stream.ERROR);
        managed.call().await();
    }

    @Override
    public void call(Path executable, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(executable, arguments);
        callAndAwait(managed);
    }

    @Override
    public void call(String command, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(command, arguments);
        callAndAwait(managed);
    }

    @Override
    public void call(Path executable, Iterable<?> arguments) throws IOException {
        final ManagedProcess managed = prepare(executable, arguments);
        callAndAwait(managed);
    }

    @Override
    public void call(String command, Iterable<?> arguments) throws IOException {
        final ManagedProcess managed = prepare(command, arguments);
        callAndAwait(managed);
    }

    private String callAndCaptureOutput(ManagedProcess managed) throws IOException {
        managed.noInput();
        managed.consume(Output.ERROR);

        final RunningProcess process = managed.call();
        // TODO handle possible exception?!
        final byte[] output = ByteStreams.toByteArray(process);
        process.await();
        return new String(output, Charsets.UTF_8);
    }

    @Override
    public String read(Path executable, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(executable, arguments);
        return callAndCaptureOutput(managed);
    }

    @Override
    public String read(String command, Object... arguments) throws IOException {
        final ManagedProcess managed = prepare(command, arguments);
        return callAndCaptureOutput(managed);
    }

    @Override
    public String read(Path executable, Iterable<?> arguments) throws IOException {
        final ManagedProcess managed = prepare(executable, arguments);
        return callAndCaptureOutput(managed);
    }

    @Override
    public String read(String command, Iterable<?> arguments) throws IOException {
        final ManagedProcess managed = prepare(command, arguments);
        return callAndCaptureOutput(managed);
    }

    @Override
    public ManagedProcess prepare(Path executable, Object... arguments) {
        final ManagedProcess managed = provider.get();
        return managed.setExecutable(executable).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(String command, Object... arguments) {
        final ManagedProcess managed = provider.get();
        return managed.setCommand(command).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(Path executable, Iterable<?> arguments) {
        final ManagedProcess managed = provider.get();
        return managed.setExecutable(executable).parameterize(arguments);
    }

    @Override
    public ManagedProcess prepare(String command, Iterable<?> arguments) {
        final ManagedProcess managed = provider.get();
        return managed.setCommand(command).parameterize(arguments);
    }

}
