# ![Caveman icon](icon.png) Primal [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform

**This library is still under development**

## Table of Contents
1\.  [Requirements](#requirements)  
2\.  [Installation](#installation)  
2.1\.  [Gradle/Maven/Ivy](#gradle/maven/ivy)  
2.2\.  [Jar](#jar)  
3\.  [Usage](#usage)  
4\.  [Design Goals](#designgoals)  
4.1\.  [Testability](#testability)  
4.2\.  [Mockability](#mockability)  
4.3\.  [Support for Dependency Injection](#supportfordependencyinjection)  
4.3.1\.  [Guice or Dagger](#guiceordagger)  
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

[PrimalUsage.java](src/spec/java/org/whiskeysierra/process/PrimalUsage.java)
```java
package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PrimalUsage {

    public void call() throws IOException {
        Primal.call("ls", "-lh");
    }

    public void read() throws IOException {
        String output = Primal.read("ls", "-lh");
        // process output further
    }

    public void cwd() throws IOException {
        final ProcessService service = Primal.createService();
        final Path path = Paths.get("/path/to/directory");
        service.prepare("ls", "-lh").in(path).call().await();
    }

}
```

[ManagedProcessUsage.java](src/spec/java/org/whiskeysierra/process/ManagedProcessUsage.java)
```java
package org.whiskeysierra.process;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.whiskeysierra.process.Redirection.appendTo;
import static org.whiskeysierra.process.Redirection.from;
import static org.whiskeysierra.process.Redirection.to;

public final class ManagedProcessUsage {

    public void cwd() throws IOException {
        final ProcessService service = Primal.createService();
        final Path workingDirectory = Paths.get("/path/to/directory");
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.in(workingDirectory);

        managed.call().await();
    }

    public void env() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.with("CLICOLOR", "0");

        managed.call().await();
    }

    public void exitValues() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        managed.allow(0, 1, 2, 3, 4);

        managed.call().await();
    }

    public void nullRedirection() throws IOException {
        final ProcessService service = Primal.createService();
        final ManagedProcess managed = service.prepare("ls", "-lh");

        // no stdin
        managed.redirect(Stream.INPUT, Redirection.NULL);
        // redirect stderr into stdout
        managed.redirect(Stream.ERROR, to(Stream.OUTPUT));
        // redirect stdout to /dev/null (or similar)
        managed.redirect(Stream.OUTPUT, Redirection.NULL);

        managed.call().await();
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

        managed.call().await();
    }

}
```

<a name="designgoals"></a>

## 4\. Design Goals

<a name="testability"></a>

### 4.1\. Testability
Dependency Injection FTW!!11!

<a name="mockability"></a>

### 4.2\. Mockability
API is pure interface-based...

```java
@Test
public void test() {
    final ProcessService service = EasyMock.createNiceMock(ProcessService.class);
    final ManagedProcess managed = EasyMock.createNiceMock(ManagedProcess.class);
    final RunningProcess process = EasyMock.createNiceMock(RunningProcess.class);

    EasyMock.expect(service.prepare("/path/to/executable")).andReturn(managed);
    EasyMock.expect(managed.call()).andReturn(process);

    final InputStream stdout = createFakeStdout();
    EasyMock.expect(process.getInput()).andReturn(stdout);
    EasyMock.expect(process.waitFor()).andReturn(0);

    EasyMock.replay(service, managed, process);

    final ExampleService unit = new ExampleService(service);
    unit.run();

    EasyMock.verify(service, managed, process);
}
```

<a name="supportfordependencyinjection"></a>

### 4.3\. Support for Dependency Injection

<a name="guiceordagger"></a>

#### 4.3.1\. Guice or Dagger

Inside your
[guice]: https://code.google.com/p/google-guice/ "Guice"
[Module](http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html) or
[dagger]: https://github.com/square/dagger "Dagger"
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

<a name="attributions"></a>

## 6\. Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
