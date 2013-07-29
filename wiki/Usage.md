### Basic Usage
Calling commands and executables, reading output as string, ...

```java
package org.whiskeysierra.process;

import com.google.common.io.ByteSource;

import java.io.IOException;

public final class BasicUsage {

    public void callUsage() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    public void toStringUsage() throws IOException {
        String output = Primal.toString("echo", "Hello", "World");
        System.out.println(output);
    }

    public void readUsage() throws IOException {
        final ByteSource output = Primal.read("echo", "Hello", "World");

        // process output further
        final byte[] bytes = output.read();
    }

}
```
[Source](../primal/blob/src/spec/java/org/whiskeysierra/process/BasicUsage.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

```java
package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Paths;

public final class ConfigurationUsage {

    public void directoryEnvironmentExitValueAndNullRedirectionUsage() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.in(Paths.get("/home"));
        managed.with("CLICOLOR", "0");
        managed.allow(0, 1);

        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.ERROR, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
```
[Source](../primal/blob/src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

#### Stream redirection
```java
package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RedirectUsage {

    public void redirectInputFromAndOutputToFile() throws IOException {
        final ProcessService service = Primal.createService();

        final Path input = Paths.get("input");
        final Path output = Paths.get("output");

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
```
[Source](../primal/blob/src/spec/java/org/whiskeysierra/process/RedirectUsage.java)

#### Process IO

JDK
```java
package org.whiskeysierra.process;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class JdkProcessIoUsage {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void test() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");
        final Path output = temp.newFile().toPath();

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            Files.copy(process.getStandardOutput(), output,
                StandardCopyOption.REPLACE_EXISTING);

            process.await();
        }
    }

}
```
[Source](../primal/blob/src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

Guava
[Guava example](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)
```java
package org.whiskeysierra.process;

import com.google.common.io.Files;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class GuavaProcessIoUsage {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void test() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final File input = Paths.get("src/test/resources/lorem-ipsum.txt").toFile();
        final File output = temp.newFile();

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input.toPath()));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            Files.copy(process, output);
            process.await();
        }
    }

}
```
[Source](../primal/blob/src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)
