### Dependency Injection

#### [Guice][guice] or [Dagger][dagger]

Inside your [Module](http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html) or
[@Module](http://square.github.io/dagger/javadoc/dagger/Module.html) respectively:

```java
@Provides
public ProcessService provideProcessService() {
    return Primal.createService();
}
```

### Mockability
API is pure interface-based...

[Mockito][mockito]

[Source](../blob/master/src/spec/java/org/whiskeysierra/process/Mockability.java)