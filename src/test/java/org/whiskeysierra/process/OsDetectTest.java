package org.whiskeysierra.process;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 *
 *
 * @see <a href="http://lopica.sourceforge.net/os.html">http://lopica.sourceforge.net</a>
 * @see <a href="http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java">http://svn.terracotta.org</a>
 */
@RunWith(Parameterized.class)
public final class OsDetectTest {

    private final String name;
    private final String version;
    private final String arch;
    private final String pathSeparator;
    private final Set<Family> families;

    public OsDetectTest(String name, String version, String arch, String pathSeparator, Set<Family> families) {
        this.name = name;
        this.version = version;
        this.arch = arch;
        this.pathSeparator = pathSeparator;
        this.families = families;
    }

    @Parameters
    public static Collection<Object[]> getExamples() {
        return Arrays.asList(new Object[][] {
            {"AIX", "4.1", "POWER_RS", ":", EnumSet.of(Family.UNIX)},
            {"AIX", "4.3", "Power", ":", EnumSet.of(Family.UNIX)},
            {"AIX", "5.2", "ppc64", ":", EnumSet.of(Family.UNIX)},
            {"Digital Unix", "4.0", "alpha", ":", EnumSet.of(Family.UNIX)},
            {"Digital", "4.0", "alpha", ":", EnumSet.of(Family.UNIX)},
            {"FreeBSD", "2.2.2-RELEASE", "x86", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.10.20", "PA-RISC", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.11.00", "PA-RISC", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.11.11", "PA-RISC", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.11.11", "PA_RISC", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.11.11", "PA_RISC2.0", ":", EnumSet.of(Family.UNIX)},
            {"HP-UX", "B.11.23", "IA64N", ":", EnumSet.of(Family.UNIX)},
            {"Irix", "6.3", "mips", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "1.1", "armv41", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "1.3", "i386", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "2.0", "i686", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "2.4", "ppc", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "2.6.34", "ppc64", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "2.6.39", "sparc", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "3.1", "x86_64", ":", EnumSet.of(Family.UNIX)},
            {"Linux", "2.0.31", "x86", ":", EnumSet.of(Family.UNIX)},
            {"MPE/iX", "C.55.00", "PA-RISC", ":", EnumSet.of(Family.UNIX)},
            {"Mac OS X", "10.1.3", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.2.6", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.2.8", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.3.1", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.3.2", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.3.3", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.3.4", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS X", "10.3.8", "ppc", ":", EnumSet.of(Family.MAC, Family.UNIX)},
            {"Mac OS", "7.5", "PowerPC", ":", EnumSet.of(Family.MAC)},
            {"Mac OS", "7.5.1", "PowerPC", ":", EnumSet.of(Family.MAC)},
            {"Mac OS", "7.5.1", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"Mac OS", "7.5.1", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"Mac OS", "8", "PowerPC", ":", EnumSet.of(Family.MAC)},
            {"Mac OS", "8.1", "PowerPC", ":", EnumSet.of(Family.MAC)},
            {"Mac OS", "8.1", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"Mac OS", "9.0", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"Mac OS", "9.2.2", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"MacOS", "8.1.0", "PowerPC", ";", EnumSet.of(Family.MAC)},
            {"NetWare 4.11", "4.11", "x86", ";", EnumSet.of(Family.NETWARE)},
            {"NetWare", "4.11", "x86", ";", EnumSet.of(Family.NETWARE)},
            {"OS/2", "20.40", "x86", ";", EnumSet.of(Family.OS_2)},
            {"OS/390", "390", "02.10.00", ";", EnumSet.of(Family.Z_OS)},
            {"OSF1", "V5.1", "alpha", ":", EnumSet.of(Family.UNIX)},
            {"OpenVMS", "V7.2-1", "alpha", ":", EnumSet.of(Family.OPENVMS)},
            {"Solaris", "2.x", "sparc", ":", EnumSet.of(Family.UNIX)},
            {"SunOS", "5.7", "sparc", ":", EnumSet.of(Family.UNIX)},
            {"SunOS", "5.8", "sparc", ":", EnumSet.of(Family.UNIX)},
            {"SunOS", "5.9", "sparc", ":", EnumSet.of(Family.UNIX)},
            {"Windows 2000", "5.0", "x86", ";", EnumSet.of(Family.WINDOWS)},
            {"Windows 2003", "5.2", "x86", ";", EnumSet.of(Family.WINDOWS)},
            {"Windows 95", "4.0", "Pentium", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows 95", "4.0", "x86", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows 98", "4.10", "x86", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows CE", "2.0 Beta", "Unknown", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows CE", "3.0 build 11171", "arm", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows Me", "4.90", "x86", ";", EnumSet.of(Family.WINDOWS, Family.WIN9X)},
            {"Windows NT", "4.0", "x86", ";", EnumSet.of(Family.WINDOWS)},
            {"Windows XP", "5.1", "x86", ";", EnumSet.of(Family.WINDOWS)},
        });
    }

    @Test
    public void testFromMap() {
        final Map<String, String> properties = Maps.newHashMap();
        properties.put("os.name", name);
        properties.put("os.version", version);
        properties.put("os.arch", arch);
        properties.put("path.separator", pathSeparator);

        final Os os = Os.from(properties);
        assertThat(os.toString(), os.getFamilies(), equalTo(families));
    }

    @Test
    public void testFromProperties() {
        final Properties properties = new Properties();
        properties.setProperty("os.name", name);
        properties.setProperty("os.version", version);
        properties.setProperty("os.arch", arch);
        properties.setProperty("path.separator", pathSeparator);

        final Os os = Os.from(properties);
        assertThat(os.toString(), os.getFamilies(), equalTo(families));
    }

}
