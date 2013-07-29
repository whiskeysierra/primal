# Primal v0.1.0 [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="icon.png" alt="Caveman icon" align="right"/>

### A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform
**This library is still under development**

The [Java Process API](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) has been around
for a while now, and even though it had received some significant updates in the past major releases
of the platform ([ProcessBuilder](http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html)),
there are still plenty of possibilities to shoot yourself in the foot:

1. [`Process.waitFor()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#waitFor\(\)) throws an
[`InterruptedException`](http://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html) but
fails to call [`Thread.interrupted()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted\(\))
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
3.2.1\.  [Stream redirection](#streamredirection)  
3.2.2\.  [Process IO](#processio)  
4\.  [Design Goals](#designgoals)  
4.1\.  [Mockability](#mockability)  
4.2\.  [Support for Dependency Injection](#supportfordependencyinjection)  
4.2.1\.  [Guice or Dagger](#guiceordagger)  
5\.  [To do](#todo)  
6\.  [References](#references)  
7\.  [Boring legal stuff](#boringlegalstuff)  
8\.  [Attributions](#attributions)  

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

<a name="advancedusage"></a>

### 3.2\. Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

[ConfigurationUsage.java](src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

<a name="streamredirection"></a>

#### 3.2.1\. Stream redirection
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

<a name="processio"></a>

#### 3.2.2\. Process IO

[JDK example](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

[Guava example](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

<a name="designgoals"></a>

## 4\. Design Goals

<a name="mockability"></a>

### 4.1\. Mockability
API is pure interface-based...

[Mockito][mockito]

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

<a name="todo"></a>

## 5\. To do
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

## 6\. References
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

## 7\. Boring legal stuff

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

## 8\. Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
