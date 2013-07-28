# Primal v0.1.0 [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="icon.png" alt="Caveman icon" align="right"/>

### A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform
**This library is still under development**

The [Java Process API](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) has been around
for a while now, and even though it had received some significant updates in the past major releases
of the platform ([ProcessBuilder](http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html)),
there are still plenty of possibilities to shoot yourself in the foot:

1. [Process.waitFor()](<http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#waitFor()>) throws an
[InterruptedException](http://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html) but
fails to call [Thread.interrupted()](<http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted()>)
which leads to subsequent `InterruptedException`s as long as the interrupted bit of the current thread is
not reset.
2. The associated output streams of a process, i.e. `stdout` and  `stderr`, must be promptly consumed or the
process will block or even end up in a deadlock.

Interesting articles on the topic can be found [here][javaworld] and [here][cnblogs].

## Table of Contents
1\.  [Requirements](#requirements)  
2\.  [Installation](#installation)  
2.1\.  [Gradle](#gradle)  
2.2\.  [Maven](#maven)  
2.3\.  [Ivy](#ivy)  
2.4\.  [Buildr](#buildr)  
2.5\.  [SBT](#sbt)  
2.6\.  [Leiningen](#leiningen)  
2.7\.  [Standalone Jar file](#standalonejarfile)  
3\.  [Usage](#usage)  
3.1\.  [Basic Usage](#basicusage)  
3.2\.  [Advanced Usage](#advancedusage)  
3.3\.  [Process IO](#processio)  
3.3.1\.  [JDK](#jdk)  
3.3.2\.  [Guava](#guava)  
4\.  [Design Goals](#designgoals)  
4.1\.  [Mockability](#mockability)  
4.2\.  [Support for Dependency Injection](#supportfordependencyinjection)  
4.2.1\.  [Guice or Dagger](#guiceordagger)  
5\.  [References](#references)  
6\.  [Attributions](#attributions)  

<a name="requirements"></a>

## 1\. Requirements

- Java 1.7 or higher
- [Guava][guava] 14.x

<a name="installation"></a>

## 2\. Installation

<a name="gradle"></a>

### 2.1\. Gradle
```groovy
compile group: 'org.whiskeysierra.process', name: 'primal', version: '0.1.0'
```

<a name="maven"></a>

### 2.2\. Maven

```xml
<dependency>
    <groupId>org.wiskeysierra.process</groupId>
    <artifactId>primal</artifactId>
    <version>0.1.0</version>
</dependency>
```

<a name="ivy"></a>

### 2.3\. Ivy
```xml
<dependency org="org.whiskeysierra.process" name="primal" rev="0.1.0"/>
```

<a name="buildr"></a>

### 2.4\. Buildr
```ruby
compile.with 'org.whiskeysierra.process:primal:jar:0.1.0'
```

<a name="sbt"></a>

### 2.5\. SBT
```scala
libraryDependencies += "org.whiskeysierra.process" % "primal" % "0.1.0"
```

<a name="leiningen"></a>

### 2.6\. Leiningen
```clojure
:dependencies [[org.whiskeysierra.process/primal "0.1.0"]]
```

<a name="standalonejarfile"></a>

### 2.7\. Standalone Jar file
Just download the jar file [here](#) and add it to your classpath. You'll also need to download all
libraries mentioned in the [Requirements sections](#requirements).

<a name="usage"></a>

## 3\. Usage

<a name="basicusage"></a>

### 3.1\. Basic Usage
Calling commands and executables, reading output as string, ...

```java
package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

public final class PrimalIntegrationTest {

    private final Path unix = Paths.get("src/test/resources/debug/script.sh");
    private final Path windows = Paths.get("src/test/resources/debug/script.bat");

    @Test
    public void callExecutableUnix() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        Primal.call(unix, "Hello", "World");
    }
    @Test
    public void callExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.WINDOWS));

        Primal.call(windows, "Hello", "World");
    }

    @Test
    public void callCommand() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    @Test
    public void readExecutableUnix() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final String output = Primal.read(unix, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readExecutableWindows() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.WINDOWS));

        final String output = Primal.read(windows, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readCommand() throws IOException {
        String output = Primal.read("echo", "Hello", "World");
        assertThat(output, equalTo("Hello World\n"));
    }

}
```
[Source](src/integration/java/org/whiskeysierra/process/PrimalIntegrationTest.java)

<a name="advancedusage"></a>

### 3.2\. Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values and
stream redirection!

```java
package org.whiskeysierra.process;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;

// TODO make abstract, implement per os family
public final class ProcessServiceIntegrationTest {

    @Rule
    public final TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void cwd() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final Path directory = Paths.get("/home");
        final ManagedProcess managed = service.prepare("ls", "-lh").in(directory);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void env() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh", "/home");

        managed.with("CLICOLOR", "0");

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void exitValues() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    // TODO add "type" test on windows
    @Test
    public void nullRedirection() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.ERROR, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    // TODO add "type" test on windows
    @Test
    public void redirectOutputToFile() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");
        final Path output = temp.newFile().toPath();

        final ManagedProcess managed = service.prepare("cat", input);

        managed.redirect(Stream.INPUT, Redirection.NULL);
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }

        final byte[] actual = Files.readAllBytes(output);
        final byte[] expected = Files.readAllBytes(input);

        assertThat(actual, equalTo(expected));
    }

    // TODO add "type" test on windows
    @Test
    public void redirectInputFromAndOutputToFile() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final ProcessService service = Primal.createService();

        final Path input = Paths.get("src/test/resources/lorem-ipsum.txt");
        final Path output = temp.newFile().toPath();

        final ManagedProcess managed = service.prepare("cat");

        managed.redirect(Stream.INPUT, Redirection.from(input));
        managed.redirect(Stream.OUTPUT, Redirection.to(output));
        managed.redirect(Stream.ERROR, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }

        final byte[] actual = Files.readAllBytes(output);
        final byte[] expected = Files.readAllBytes(input);

        assertThat(actual, equalTo(expected));
    }

}
```
[Source](src/integration/java/org/whiskeysierra/process/ProcessServiceIntegrationTest.java)

<a name="processio"></a>

### 3.3\. Process IO

<a name="jdk"></a>

#### 3.3.1\. JDK
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
[Source](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

<a name="guava"></a>

#### 3.3.2\. Guava
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
[Source](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

<a name="designgoals"></a>

## 4\. Design Goals

<a name="mockability"></a>

### 4.1\. Mockability
API is pure interface-based...

[Mockito][mockito]

```java
package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class Mockability {

    private static final class ExampleService {

        private final ProcessService service;

        public ExampleService(ProcessService service) {
            this.service = service;
        }

        public String run() throws IOException {
            return service.read(Paths.get("path/to/your/executable"));
        }

    }

    @Test
    public void test() throws IOException {
        final ProcessService service = mock(ProcessService.class);

        final String expected = "Hello World";

        when(service.read(any(Path.class))).thenReturn(expected);

        final ExampleService unit = new ExampleService(service);
        final String actual = unit.run();

        assertThat(actual, equalTo(expected));
    }

}
```
[Source](src/spec/java/org/whiskeysierra/process/Mockability.java)

<a name="supportfordependencyinjection"></a>

### 4.2\. Support for Dependency Injection

<a name="guiceordagger"></a>

#### 4.2.1\. [Guice][guice] or [Dagger][dagger]

Inside your [Module](http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html) or
[@Module](http://square.github.io/dagger/javadoc/dagger/Module.html) respectively:

```java
@Provides
public ProcessService provideProcessService() {
    return Primal.createService();
}
```

<a name="references"></a>

## 5\. References
*	[Guice][guice]
*	[Dagger][dagger]
*	[Mockito][mockito]
*	[When Runtime.exec() won't][javaworld]
*	[java.lang.Process Pitfalls][cnblogs]

[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"

<a name="attributions"></a>

## 6\. Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
