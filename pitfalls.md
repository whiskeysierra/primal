---
layout: page
permalink: /pitfalls/index.html
title: java.lang.Process Pitfalls
description: "A Process Management Library for Java"
tags: [java, process api]
image:
  feature: texture-feature-02.jpg
version: 0.2.0
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

## Thread interrupt
[`Process.waitFor()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html#waitFor\(\))
throws an [`InterruptedException`](http://docs.oracle.com/javase/7/docs/api/java/lang/InterruptedException.html) but
fails to call [`Thread.interrupted()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted\(\))
which leads to subsequent `InterruptedException`s as long as the interrupted bit of the current thread is
not reset.

## Stream consumption
The associated output streams of a process, i.e. `stdout` and  `stderr`, must be promptly consumed or the
process will block or even end up in a deadlock.

## Stackoverflow

> If your external process expects something on its `stdin`,
> you MUST close the `getOutputStream`. Otherwise you will `waitFor` forever.
>
> -- [<cite>Alexander Pogrebnyak</cite>](http://stackoverflow.com/questions/2150723/process-waitfor-threads-and-inputstreams/2151169#2151169)

## References

Michael C. Daconta. (2000). *When Runtime.exec() won't*. [URL](http://www.javaworld.com/jw-12-2000/jw-1229-traps.html).

Kyle W. Cartmell. (2009). *Five Common java.lang.Process Pitfalls*.
[Archived](http://web.archive.org/web/20120927021125/http://kylecartmell.com/?p=9)
[Original URL](http://kylecartmell.com/?p=9)

*Process.waitFor(), threads, and InputStreams*.
[URL](http://stackoverflow.com/questions/2150723/process-waitfor-threads-and-inputstreams/2151169)

{% include attributions.md %}

[guava]: https://code.google.com/p/guava-libraries/ "Guava"
[guice]: https://code.google.com/p/google-guice/ "Guice"
[dagger]: http://square.github.io/dagger/ "Dagger"
[mockito]: https://code.google.com/p/mockito/ "Mockito"
[javaworld]: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html "When Runtime.exec() won't"
[cnblogs]: http://www.cnblogs.com/abnercai/archive/2012/12/27/2836008.html "java.lang.Process Pitfalls"