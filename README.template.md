# ![Caveman icon](icon.png) Primal v#VERSION# [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform

**This library is still under development**

## Table of Contents
!TOC

## Requirements

- Java 1.6 or higher
- Guava 14.x

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

## Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html) 
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
