package org.whiskeysierra.process.internal.redirect;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import org.whiskeysierra.process.Family;
import org.whiskeysierra.process.Os;
import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;
import org.whiskeysierra.process.spi.Redirector;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

final class DefaultRedirector implements Redirector {

    // TODO test defaults
    private final Map<Stream, Redirection> DEFAULTS = new Builder<Stream, Redirection>().
        put(Stream.INPUT, Redirection.PIPE).
        put(Stream.OUTPUT, Redirection.PIPE).
        put(Stream.ERROR, Redirection.PIPE).
        build();

    private final Os os;
    private final Path nullDevice;
    private final Redirect nullSource;
    private final Redirect nullTarget;

    @Inject
    DefaultRedirector(Os os) {
        this.os = os;
        this.nullDevice = getNullDevice();
        this.nullSource = createNullSource();
        this.nullTarget = createNullTarget();
    }

    @Nullable
    private Path choose(String... choices) {
        for (String choice : choices) {
            final Path path = Paths.get(choice);
            if (Files.isReadable(path) && Files.isWritable(path)) {
                return path;
            }
        }
        return null;
    }

    /**
     *
     * @see <a href="http://en.wikipedia.org/wiki//dev/null">http://en.wikipedia.org/wiki//dev/null</a>
     * @see <a href="http://en.wikipedia.org/wiki/NUL:">http://en.wikipedia.org/wiki/NUL:</a>
     */
    private Path getNullDevice() {
        if (os.getFamilies().contains(Family.UNIX)) {
            return choose("/dev/null");
        } else if (os.getFamilies().contains(Family.WINDOWS)) {
            return choose("\\Device\\Null", "NUL", "NUL:", "nul", "nul:");
        } else if (os.getFamilies().contains(Family.OPENVMS)) {
            return choose("NL:");
        } else {
            // TODO find a list of null devices for other OSes
            return null;
        }
    }

    private Redirect createNullSource() {
        if (nullDevice == null) {
            return null;
        } else {
            return Redirect.from(nullDevice.toFile());
        }
    }

    private Redirect createNullTarget() {
        if (nullDevice == null) {
            return null;
        } else {
            return Redirect.to(nullDevice.toFile());
        }
    }

    @Override
    public Map<Stream, Redirection> getDefaults() {
        return Maps.newHashMap(DEFAULTS);
    }

    @Override
    public Redirect fromNullDevice() {
        return nullSource;
    }

    @Override
    public Redirect toNullDevice() {
        return nullTarget;
    }

}
