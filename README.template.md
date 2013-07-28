# Primal v#VERSION# [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

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
!TOC

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
!INCLUDE "src/integration/java/org/whiskeysierra/process/PrimalIntegrationTest.java"
```
[Source](src/integration/java/org/whiskeysierra/process/PrimalIntegrationTest.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values and
stream redirection!

```java
!INCLUDE "src/integration/java/org/whiskeysierra/process/ProcessServiceIntegrationTest.java"
```
[Source](src/integration/java/org/whiskeysierra/process/ProcessServiceIntegrationTest.java)

### Process IO

#### JDK
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java"
```
[Source](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

#### Guava
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java"
```
[Source](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

## Design Goals

### Mockability
API is pure interface-based...

[Mockito][mockito]

```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/Mockability.java"
```
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

## References
!REF

[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"

## Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
