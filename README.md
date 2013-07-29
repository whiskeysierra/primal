# Primal v0.1.0 [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="docs/icon.png" alt="Caveman icon" align="right"/>

### A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform
**This library is still under development**

The [Java Process API](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) has been around
for a while now, and even though it had received some significant updates in the past major releases
of the platform, there are still plenty of possibilities to shoot yourself in the foot. For more details
read the section about [Issues with the Process API](#issueswiththeprocessapi).

The goal of this library is to provide a usable API to be used as an alternative to
[`Process`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) and
[`ProcessBuilder`](http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html) which

1. works around the issues with Process API
2. provide a set of useful [Features](#features)

## Table of Contents
1\.  [Issues with the Process API](#issueswiththeprocessapi)  
2\.  [Features](#features)  
3\.  [Requirements](#requirements)  
4\.  [Installation](#installation)  
5\.  [Usage](#usage)  
6\.  [Design Goals](#designgoals)  
7\.  [TODO](#todo)  
8\.  [References](#references)  
9\.  [Boring legal stuff](#boringlegalstuff)  
10\.  [Attributions](#attributions)  

<a name="issueswiththeprocessapi"></a>

## 1\. Issues with the Process API

1. [`Process.waitFor()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#waitFor\(\)) throws an
[`InterruptedException`](http://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html) but
fails to call [`Thread.interrupted()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted\(\))
which leads to subsequent `InterruptedException`s as long as the interrupted bit of the current thread is
not reset.
2. The associated output streams of a process, i.e. `stdout` and  `stderr`, must be promptly consumed or the
process will block or even end up in a deadlock.

Interesting articles on the topic can be found [here][javaworld] and [here][cnblogs].

<a name="features"></a>

## 2\. Features

1. Interface-based API
2. Efficient and cross-platform stream gobbling

<a name="requirements"></a>

## 3\. Requirements

- Java 1.7 or higher
- [Guava][guava] 14.x

<a name="installation"></a>

## 4\. Installation

### Gradle
```groovy
compile group: 'org.whiskeysierra.process', name: 'primal', version: '0.1.0'
```

### Maven

```xml
<dependency>
    <groupId>org.wiskeysierra.process</groupId>
    <artifactId>primal</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Ivy
```xml
<dependency org="org.whiskeysierra.process" name="primal" rev="0.1.0"/>
```

### Buildr
```ruby
compile.with 'org.whiskeysierra.process:primal:jar:0.1.0'
```

### SBT
```scala
libraryDependencies += "org.whiskeysierra.process" % "primal" % "0.1.0"
```

### Leiningen
```clojure
:dependencies [[org.whiskeysierra.process/primal "0.1.0"]]
```

### Standalone Jar file
Just download the jar file [here](#) and add it to your classpath. You'll also need to download all
libraries mentioned in the [Requirements sections](#requirements).

<a name="usage"></a>

## 5\. Usage

### Basic Usage
Calling commands and executables, reading output as string, ...

```java
package org.whiskeysierra.process;

import java.io.IOException;

public final class BasicUsage {

    public void call() throws IOException {
        Primal.call("echo", "Hello", "World");
    }

    public void read() throws IOException {
        String output = Primal.read("echo", "Hello", "World");
        System.out.println(output);
    }

}
```
[Source](src/spec/java/org/whiskeysierra/process/BasicUsage.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

[ConfigurationUsage.java](src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

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
[Source](src/spec/java/org/whiskeysierra/process/RedirectUsage.java)

#### Process IO

[JDK example](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

[Guava example](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

<a name="designgoals"></a>

## 6\. Design Goals

### Mockability
API is pure interface-based...

[Mockito][mockito]

[Source](src/spec/java/org/whiskeysierra/process/Mockability.java)

### Support for Dependency Injection

#### [Guice][guice] or [Dagger][dagger]

Inside your [Module](http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html) or
[@Module](http://square.github.io/dagger/javadoc/dagger/Module.html) respectively:

```java
@Provides
public ProcessService provideProcessService() {
    return Primal.createService();
}
```

<a name="todo"></a>

## 7\. TODO
- `final byte[] output = ManagedProcess.read()`
  - readFully into `ByteSource` via `ManagedProcess.readFully()`?
- `ByteSource ProcessService.read()`?
  - Specify, that ProcessService works in memory on the stream
- `ManagedProcess.callAndIgnore()`?
- custom `Exception` type?
  - `ProcessException extends RuntimeException`?
- command vs. executable
- test suite
- cross platform testing
- autoclose on exception?
  - inner wrapper classes for streams?!
- Debug mode
  - Command line output
  - Debug scripts?
- http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html
- http://www.javaworld.com/jw-12-2000/jw-1229-traps.html
- http://commons.apache.org/proper/commons-exec/
- Argument escaping
- Argument string converting (Flux?!)
- Argument placeholder/substitution?
- Timeouts/Watchdogs
  - ExecutorService?

<a name="references"></a>

## 8\. References
*	[Guava][guava]
*	[Guice][guice]
*	[Dagger][dagger]
*	[Mockito][mockito]
*	[When Runtime.exec() won't][javaworld]
*	[java.lang.Process Pitfalls][cnblogs]

[guava]: https://code.google.com/p/guava-libraries/ "Guava"
[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"

<a name="boringlegalstuff"></a>

## 9\. Boring legal stuff
The MIT License (MIT)

Copyright (c) 2013 Willi Schönborn

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

<a name="attributions"></a>

## 10\. Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
