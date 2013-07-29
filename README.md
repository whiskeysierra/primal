# Primal [![Build Status](https://travis-ci.org/whiskeysierra/primal.png?branch=master)](http://travis-ci.org/whiskeysierra/primal)

<img src="site/images/icon.png" alt="Caveman icon" align="right"/>

### A **Pr**ocess **Ma**nagement **L**ibrary for the Java Platform
**This library is still under development**

## Requirements

- Java 1.7 or higher
- see [`build.gradle`](https://github.com/whiskeysierra/primal/blob/master/build.gradle#L30)

## TODO
- Site
  - site url locally vs. remote
  - dynamic version pages
- `ByteSource ManagedProcess.read()`?
  - Specify, that ProcessService works in memory on the stream
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

## Boring legal stuff
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

## Attributions
Caveman Icon by [Fast Icon](http://www.iconarchive.com/show/dino-icons-by-fasticon/Caveman-rock-2-icon.html)
is licensed as Linkware: [Icons by: Fast Icon.com](http://www.fasticon.com/)
