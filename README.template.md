# ![Caveman icon](icon.png) Primal [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform

**This library is still under development**

## Table of Contents
!TOC

## Requirements

- Java 1.6 or higher
- Guava 14.x

## Installation

### Gradle/Maven/Ivy

### Jar

## Usage

### Basic Usage

[PrimalUsage.java](src/spec/java/org/whiskeysierra/process/PrimalUsage.java)
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/PrimalUsage.java"
```

### Advanced Usage

[ManagedProcessUsage.java](src/spec/java/org/whiskeysierra/process/ManagedProcessUsage.java)
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/ManagedProcessUsage.java"
```

### Process IO

[JdkProcessIoUsage.java](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java"
```

[GuavaProcessIoUsage.java](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/ManagedProcessUsage.java"
```

## Design Goals

### Mockability
API is pure interface-based...

[Mockito][mockito]

[ManagedProcessUsage.java](src/spec/java/org/whiskeysierra/process/Mockability.java)
```java
!INCLUDE "src/spec/java/org/whiskeysierra/process/Mockability.java"
```

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
