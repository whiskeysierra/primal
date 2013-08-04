package org.whiskeysierra.process.internal.os;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.annotation.concurrent.Immutable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

@Immutable
public final class Os {

    /**
     * lazy initialization holder class idiom
     * TODO casing
     * TODO @see J. Block, item xxx
     */
    private static final class Lazy {
        private static final Os SINGLETON = from(System.getProperties());
    }

    private final String name;
    private final String version;
    private final String arch;
    private final String pathSeparator;
    private final String fileSeparator;
    private final Set<Family> families;

    private Os(String name, String version, String arch, String pathSeparator, String fileSeparator,
        Set<Family> families) {
        this.name = name;
        this.version = version;
        this.arch = arch;
        this.pathSeparator = pathSeparator;
        this.fileSeparator = fileSeparator;
        this.families = Sets.immutableEnumSet(families);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getArch() {
        return arch;
    }

    public String getPathSeparator() {
        return pathSeparator;
    }

    public String getFileSeparator() {
        return fileSeparator;
    }

    public Set<Family> getFamilies() {
        return families;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Os) {
            final Os other = Os.class.cast(that);
            return Objects.equals(this.getName(), other.getName()) &&
                Objects.equals(this.getVersion(), other.getVersion()) &&
                Objects.equals(this.getArch(), other.getArch()) &&
                Objects.equals(this.getPathSeparator(), other.getPathSeparator()) &&
                Objects.equals(this.getFileSeparator(), other.getFileSeparator()) &&
                Objects.equals(this.getFamilies(), other.getFamilies());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVersion(), getArch(),
            getPathSeparator(), getFileSeparator(), getFamilies());
    }

    @Override
    public String toString() {
        return String.format("%s [%s, %s, pathsep='%s', filesep='%s', families=%s]",
            name, version, arch, pathSeparator, fileSeparator, families);
    }

    public static Os getCurrent() {
        return Lazy.SINGLETON;
    }

    // Inspects os.name, os.arch, os.version and path.separator!
    public static Os from(Properties properties) {
        return from(Maps.fromProperties(properties));
    }

    private static String safeGet(Map<String, String> map, String key) {
        return Preconditions.checkNotNull(map.get(key), key);
    }

    public static Os from(Map<String, String> properties) {
        final String name = safeGet(properties, "os.name");
        final String version = safeGet(properties, "os.version");
        final String arch = safeGet(properties, "os.arch");
        final String pathSeparator = safeGet(properties, "path.separator");
        final String fileSeparator = safeGet(properties, "file.separator");

        return of(name, version, arch, pathSeparator, fileSeparator);
    }

    private static Os of(String name, String version, String arch, String pathSeparator, String fileSeparator) {
        final Set<Family> families = EnumSet.noneOf(Family.class);

        for (Family family : Family.values()) {
            // TODO specify casing
            if (family.matches(name.toLowerCase(Locale.ENGLISH), pathSeparator)) {
                families.add(family);
            }
        }

        Preconditions.checkState(!families.isEmpty(), "No family found for '%s'", name);
        return new Os(name, version, arch, pathSeparator, fileSeparator, families);
    }

    @VisibleForTesting
    static Os of(String name, String version, String arch, String pathSeparator, String fileSeparator, Family family, Family... families) {
        return new Os(name, version, arch, pathSeparator, fileSeparator, EnumSet.of(family, families));
    }

}
