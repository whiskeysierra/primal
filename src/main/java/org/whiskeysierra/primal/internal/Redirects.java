package org.whiskeysierra.primal.internal;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import org.whiskeysierra.primal.Stream;
import org.whiskeysierra.primal.internal.os.Family;
import org.whiskeysierra.primal.internal.os.Os;

import javax.annotation.Nullable;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

final class Redirects {

    // TODO test defaults
    private static final Map<Stream, Redirect> DEFAULTS = new Builder<Stream, Redirect>().
        put(Stream.INPUT, Redirect.PIPE).
        put(Stream.OUTPUT, Redirect.PIPE).
        put(Stream.ERROR, Redirect.PIPE).
        build();

    /**
     *
     * @see <a href="http://en.wikipedia.org/wiki//dev/null">http://en.wikipedia.org/wiki//dev/null</a>
     * @see <a href="http://en.wikipedia.org/wiki/NUL:">http://en.wikipedia.org/wiki/NUL:</a>
     */
    private static final class NullDevice {

        private static final Path INSTANCE = getNullDevice();

        @Nullable
        private static Path choose(String... choices) {
            for (String choice : choices) {
                final Path path = Paths.get(choice);
                if (Files.isReadable(path) && Files.isWritable(path)) {
                    return path;
                }
            }
            return null;
        }

        private static Path getNullDevice() {
            // TODO don't do it here, use DI
            final Os os = Os.getCurrent();

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

    }

    private static final class Lazy {

        private static final Redirect FROM = createNullSource();
        private static final Redirect TO = createNullTarget();

        private static Redirect createNullSource() {
            if (NullDevice.INSTANCE == null) {
                return null;
            } else {
                return Redirect.from(NullDevice.INSTANCE.toFile());
            }
        }

        private static Redirect createNullTarget() {
            if (NullDevice.INSTANCE == null) {
                return null;
            } else {
                return Redirect.to(NullDevice.INSTANCE.toFile());
            }
        }

    }

    public static Map<Stream, Redirect> getDefaults() {
        return Maps.newHashMap(DEFAULTS);
    }

    public static Redirect fromNullDevice() {
        return Lazy.FROM;
    }

    public static Redirect toNullDevice() {
        return Lazy.TO;
    }

}
