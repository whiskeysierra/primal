### Gradle
```groovy
compile group: 'org.whiskeysierra.process', name: 'primal', version: '{{ version }}'
```

### Maven

```xml
<dependency>
    <groupId>org.wiskeysierra.process</groupId>
    <artifactId>primal</artifactId>
    <version>{{ version }}</version>
</dependency>
```

### Ivy
```xml
<dependency org="org.whiskeysierra.process" name="primal" rev="{{ version }}"/>
```

### Buildr
```ruby
compile.with 'org.whiskeysierra.process:primal:jar:{{ version }}'
```

### SBT
```scala
libraryDependencies += "org.whiskeysierra.process" % "primal" % "{{ version }}"
```

### Leiningen
```clojure
:dependencies [[org.whiskeysierra.process/primal "{{ version }}"]]
```

### Standalone Jar file
Just download the jar file [here](#) and add it to your classpath. You'll also need to download all
libraries mentioned in the [Requirements sections](#requirements).
