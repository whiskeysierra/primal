# ![Caveman icon](icon.png) Primal [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform

**This library is still under development**

## Table of Contents
1\.  [Requirements](#requirements)  
2\.  [Installation](#installation)  
2.1\.  [Gradle/Maven/Ivy](#gradle/maven/ivy)  
2.2\.  [Jar](#jar)  
3\.  [Usage](#usage)  
3.1\.  [Basic Usage](#basicusage)  
3.2\.  [Advanced Usage](#advancedusage)  
3.3\.  [Process IO](#processio)  
4\.  [Design Goals](#designgoals)  
4.1\.  [Mockability](#mockability)  
4.2\.  [Support for Dependency Injection](#supportfordependencyinjection)  
4.2.1\.  [Guice or Dagger](#guiceordagger)  
5\.  [References](#references)  
6\.  [Attributions](#attributions)  

<a name="requirements"></a>

## 1\. Requirements

- Java 1.6 or higher
- Guava 14.x

<a name="installation"></a>

## 2\. Installation

<a name="gradle/maven/ivy"></a>

### 2.1\. Gradle/Maven/Ivy

<a name="jar"></a>

### 2.2\. Jar

<a name="usage"></a>

## 3\. Usage

<a name="basicusage"></a>

### 3.1\. Basic Usage

[PrimalUsage.java](src/spec/java/org/whiskeysierra/process/PrimalUsage.java)
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

public final class PrimalUsage {

    private final Path executable = Paths.get("src/test/resources/debug/script.sh");

    @Test
    public void callExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        Primal.call(executable, "Hello", "World");
    }

    @Test
    public void callCommand() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    @Test
    public void readExecutable() throws IOException {
        assumeThat(Os.getCurrent().getFamilies(), hasItem(Family.UNIX));

        final String output = Primal.read(executable, "Hello", "World");
        assertThat(output, equalTo("Hello\nWorld\n"));
    }

    @Test
    public void readCommand() throws IOException {
        String output = Primal.read("echo", "Hello", "World");
        assertThat(output, equalTo("Hello World\n"));
    }

}
```

<a name="advancedusage"></a>

### 3.2\. Advanced Usage

[ManagedProcessUsage.java](src/spec/java/org/whiskeysierra/process/ManagedProcessUsage.java)
```java
package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;
import static org.whiskeysierra.process.Redirection.appendTo;
import static org.whiskeysierra.process.Redirection.from;
import static org.whiskeysierra.process.Redirection.to;

public final class ManagedProcessUsage {

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
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void nullRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        // no stdin
        managed.redirect(Stream.INPUT, Redirection.NULL);
        // redirect stderr into stdout
        managed.redirect(Stream.ERROR, to(Stream.OUTPUT));
        // redirect stdout to /dev/null (or similar)
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    public void fileRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        final Path input = Paths.get("stdin.txt");
        final Path error = Paths.get("stderr.log");
        final Path output = Paths.get("stdout.log");

        // read stdin from stdin.txt
        managed.redirect(Stream.INPUT, from(input));
        // redirect to stderr.log (overwrite)
        managed.redirect(Stream.ERROR, to(error));
        // append to stdout.log
        managed.redirect(Stream.OUTPUT, appendTo(output));

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
```

<a name="processio"></a>

### 3.3\. Process IO

[JdkProcessIoUsage.java](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)
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

[GuavaProcessIoUsage.java](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)
```java
package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assume.assumeThat;
import static org.whiskeysierra.process.Redirection.appendTo;
import static org.whiskeysierra.process.Redirection.from;
import static org.whiskeysierra.process.Redirection.to;

public final class ManagedProcessUsage {

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
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    @Test
    public void nullRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        // no stdin
        managed.redirect(Stream.INPUT, Redirection.NULL);
        // redirect stderr into stdout
        managed.redirect(Stream.ERROR, to(Stream.OUTPUT));
        // redirect stdout to /dev/null (or similar)
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

    public void fileRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        final Path input = Paths.get("stdin.txt");
        final Path error = Paths.get("stderr.log");
        final Path output = Paths.get("stdout.log");

        // read stdin from stdin.txt
        managed.redirect(Stream.INPUT, from(input));
        // redirect to stderr.log (overwrite)
        managed.redirect(Stream.ERROR, to(error));
        // append to stdout.log
        managed.redirect(Stream.OUTPUT, appendTo(output));

        try (RunningProcess process = managed.call()) {
            process.await();
        }
    }

}
```

<a name="designgoals"></a>

## 4\. Design Goals

<a name="mockability"></a>

### 4.1\. Mockability
API is pure interface-based...

[Mockito][mockito]

[ManagedProcessUsage.java](src/spec/java/org/whiskeysierra/process/Mockability.java)
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

[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"

<a name="attributions"></a>

## 6\. Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
