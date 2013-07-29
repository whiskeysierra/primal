# Primal v#VERSION# [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="icon.png" alt="Caveman icon" align="right"/>

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
!TOC

## Issues with the Process API

1. [`Process.waitFor()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#waitFor\(\)) throws an
[`InterruptedException`](http://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html) but
fails to call [`Thread.interrupted()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted\(\))
which leads to subsequent `InterruptedException`s as long as the interrupted bit of the current thread is
not reset.
2. The associated output streams of a process, i.e. `stdout` and  `stderr`, must be promptly consumed or the
process will block or even end up in a deadlock.

Interesting articles on the topic can be found [here][javaworld] and [here][cnblogs].

## Features

1. Interface-based API
2. Efficient and cross-platform stream gobbling

## Requirements

- Java 1.7 or higher
- [Guava][guava] 14.x

## Installation

### Gradle
```groovy
compile group: 'org.whiskeysierra.process', name: 'primal', version: '#VERSION#'
```

### Maven

```xml
<dependency>
    <groupId>org.wiskeysierra.process</groupId>
    <artifactId>primal</artifactId>
    <version>#VERSION#</version>
</dependency>
```

### Ivy
```xml
<dependency org="org.whiskeysierra.process" name="primal" rev="#VERSION#"/>
```

### Buildr
```ruby
compile.with 'org.whiskeysierra.process:primal:jar:#VERSION#'
```

### SBT
```scala
libraryDependencies += "org.whiskeysierra.process" % "primal" % "#VERSION#"
```

### Leiningen
```clojure
:dependencies [[org.whiskeysierra.process/primal "#VERSION#"]]
```

### Standalone Jar file
Just download the jar file [here](#) and add it to your classpath. You'll also need to download all
libraries mentioned in the [Requirements sections](#requirements).

## Usage

### Basic Usage
Calling commands and executables, reading output as string, ...

```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/BasicUsage.java"
```
[Source](src/spec/java/org/whiskeysierra/process/BasicUsage.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

[ConfigurationUsage.java](src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

#### Stream redirection
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/RedirectUsage.java"
```
[Source](src/spec/java/org/whiskeysierra/process/RedirectUsage.java)

#### Process IO

[JDK example](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

[Guava example](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

## Design Goals

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

## To do
!INCLUDE "TODO.md"

## References
!REF

[guava]: https://code.google.com/p/guava-libraries/ "Guava"
[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"

## Boring legal stuff

!INCLUDE "LICENSE"

## Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
