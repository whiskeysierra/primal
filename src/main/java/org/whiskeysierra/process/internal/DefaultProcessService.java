package org.whiskeysierra.process.internal;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.whiskeysierra.process.ManagedProcess;
import org.whiskeysierra.process.ProcessService;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.RunningProcess;
import org.whiskeysierra.process.Stream;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.nio.file.Path;

final class DefaultProcessService implements ProcessService {

    private final Provider<ManagedProcess> provider;

    @Inject
    DefaultProcessService(Provider<ManagedProcess> provider) {
        this.provider = provider;
    }

    private void callAndAwait(ManagedProcess managed) throws IOException {
        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.NULL);
        managed.redirect(Stream.ERROR, Redirection.NULL);
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
        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.ERROR, Redirection.NULL);

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
