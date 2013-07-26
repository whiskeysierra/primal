package org.whiskeysierra.primal.internal.os;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import javax.annotation.concurrent.Immutable;
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
    private final String arch;
    private final String version;
    private final Set<Family> families;

    private Os(String name, String arch, String version, Set<Family> families) {
        this.name = name;
        this.arch = arch;
        this.version = version;
        // TODO immutable enum set
        this.families = families;
    }

    public String getName() {
        return name;
    }

    public String getArch() {
        return arch;
    }

    public String getVersion() {
        return version;
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
                Objects.equals(this.getArch(), other.getArch()) &&
                Objects.equals(this.getVersion(), other.getVersion()) &&
                Objects.equals(this.getFamilies(), other.getFamilies());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getArch(), getVersion(), getFamilies());
    }

    public static Os getCurrent() {
        return Lazy.SINGLETON;
    }

    /**
     * Inspects os.name, os.arch and os.version!
     *
     * @param properties
     * @return
     */
    public static Os from(Properties properties) {
        return from(Maps.fromProperties(properties));
    }

    public static Os from(Map<String, String> properties) {
        throw new UnsupportedOperationException();
    }

    @VisibleForTesting
    static Os of(String name, String arch, String version, Family... families) {
        throw new UnsupportedOperationException();
    }

}
