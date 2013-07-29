---
layout: page
permalink: /index.html
title: Primal
description: "A Process Management Library for Java"
tags: [java, process api]
image:
  feature: texture-feature-01.jpg
version: 0.1.0
---

<section id="table-of-contents" class="toc">
  <header>
    <h3 class="delta">Contents</h3>
  </header>
<div id="drawer" markdown="1">
*  Auto generated table of contents
{:toc 1}
</div>
</section><!-- /#table-of-contents -->

### A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform

<br/>

<img src="images/icon.png" alt="Caveman icon" align="right"/>

The [Java Process API](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) has been around
for a while now, and even though it had received some significant updates in the past major releases
of the platform, there are still plenty of possibilities to shoot yourself in the foot. For more details
read the section about [Issues with the Process API](#issues-with-the-process-api).

The goal of this library is to provide a usable API to be used as an alternative to
[`Process`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html) and
[`ProcessBuilder`](http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html) which

1. works around the issues with Process API
2. provide a set of useful [features](#features)

## Requirements

- Java 1.7 or higher
- see [`build.gradle`](https://github.com/whiskeysierra/primal/blob/master/build.gradle#L30)

## Installation

### Gradle

{% highlight groovy %}
compile 'org.whiskeysierra.process:primal:{{ page.version }}'
{% endhighlight %}

### Maven

{% highlight xml %}
<dependency>
    <groupId>org.wiskeysierra.process</groupId>
    <artifactId>primal</artifactId>
    <version>{{ page.version }}</version>
</dependency>
{% endhighlight %}

### Ivy
{% highlight xml %}
<dependency org="org.whiskeysierra.process" name="primal" rev="{{ page.version }}"/>
{% endhighlight %}

### Buildr
{% highlight ruby %}
compile.with 'org.whiskeysierra.process:primal:jar:{{ page.version }}'
{% endhighlight %}

### SBT
{% highlight scala %}
libraryDependencies += "org.whiskeysierra.process" % "primal" % "{{ page.version }}"
{% endhighlight %}

### Leiningen
{% highlight clojure %}
:dependencies [[org.whiskeysierra.process/primal "{{ page.version }}"]]
{% endhighlight %}

### Standalone Jar file
Just download the jar file [here](#) and add it to your classpath. You'll also need to download all
libraries mentioned in the [Requirements sections](#requirements).

## Usage

### Basic Usage
Calling commands and executables, reading output as string, ...

{% highlight java %}
Primal.call("echo", "Hello", "World");
{% endhighlight %}

{% highlight java %}
String output = Primal.toString("echo", "Hello", "World");
{% endhighlight %}

{% highlight java %}
final ByteSource output = Primal.read("echo", "Hello", "World");

// process output further
final byte[] bytes = output.read();
{% endhighlight %}

[Source](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/BasicUsage.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

{% highlight java %}
final ProcessService service = Primal.createService();
final ManagedProcess managed = service.prepare("ls", "-lh");

managed.in(Paths.get("/home"));
managed.with("CLICOLOR", "0");
managed.allow(0, 1);

managed.redirect(Stream.INPUT, Redirection.NULL);
managed.redirect(Stream.ERROR, Redirection.NULL);
managed.redirect(Stream.OUTPUT, Redirection.NULL);

try (RunningProcess process = managed.call()) {
    process.await();
}
{% endhighlight %}
[Source](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

#### Stream redirection
{% highlight java %}
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
{% endhighlight %}
[Source](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/RedirectUsage.java)

#### Process IO

[JDK example](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)
[Guava example](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)

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

## Design Goals

### Dependency Injection

#### [Guice][guice] or [Dagger][dagger]

Inside your [Module](http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html) or
[@Module](http://square.github.io/dagger/javadoc/dagger/Module.html) respectively:

{% highlight java %}
@Provides
public ProcessService provideProcessService() {
    return Primal.createService();
}
{% endhighlight %}

### Mockability
API is pure interface-based...

[Mockito][mockito]

[Source](https://github.com/whiskeysierra/primal/blob/master/src/spec/java/org/whiskeysierra/process/Mockability.java)

## Boring legal stuff
{% include LICENSE %}

{% include Attributions.md %}

[guava]: https://code.google.com/p/guava-libraries/ "Guava"
[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"
