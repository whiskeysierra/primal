# Primal [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="wiki/icon.png" alt="Caveman icon" align="right"/>

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
!TOC 1

## Features

1. Interface-based API
2. Efficient and cross-platform stream gobbling

## Requirements

- Java 1.7 or higher
- see [`build.gradle`](https://github.com/whiskeysierra/primal/blob/master/build.gradle#L30)

{% include "TODO.md" %}

## References
!REF

[guava]: https://code.google.com/p/guava-libraries/ "Guava"
[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"

## Boring legal stuff
{% include "LICENSE" %}

{% include "wiki/Attributions.md" %}
