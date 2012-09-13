# Gradle Cobertura Plugin [![Build Status](https://buildhive.cloudbees.com/job/Mapvine/job/gradle-cobertura-plugin/badge/icon)](https://buildhive.cloudbees.com/job/Mapvine/job/gradle-cobertura-plugin/)
Produces code coverage reports for your JVM-based projects using [Cobertura](http://cobertura.sourceforge.net/)

## Quick Start

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.mapvine:gradle-cobertura-plugin:0.3.1'
    }
}

apply plugin: 'cobertura'

cobertura {
    format = 'xml'
    includes = ['**/*.java', '**/*.groovy']
}
```

## Configuration

* _(Optional)_ format = 'html' (default) or 'xml'
* _(Optional)_ includes = List<String> glob paths to be reported on
* _(Optional)_ excludes = List<String> glob paths to exclude from reporting
* _(Optional)_ ignores = List<String> regexes of classes to exclude from instrumentation

### With `java` plugin

When using in conjunction with the Java plugin, the `test` task is preconfigured for code coverage analysis. To generate the coverage report, run the `testCoberturaReport` task.

## License
This plugin is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
