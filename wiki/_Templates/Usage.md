## Usage

### Basic Usage
Calling commands and executables, reading output as string, ...

```java
{% include "src/spec/java/org/whiskeysierra/process/BasicUsage.java" %}
```
[Source](src/spec/java/org/whiskeysierra/process/BasicUsage.java)

### Advanced Usage
Setting environment variables, changing working directory, specify allowed exit values

```java
{% include "src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java" %}
```
[Source](src/spec/java/org/whiskeysierra/process/ConfigurationUsage.java)

#### Stream redirection
```java
{% include "src/spec/java/org/whiskeysierra/process/RedirectUsage.java" %}
```
[Source](src/spec/java/org/whiskeysierra/process/RedirectUsage.java)

#### Process IO

JDK
```java
{% include "src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java" %}
```
[Source](src/spec/java/org/whiskeysierra/process/JdkProcessIoUsage.java)

Guava
[Guava example](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)
```java
{% include "src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java" %}
```
[Source](src/spec/java/org/whiskeysierra/process/GuavaProcessIoUsage.java)