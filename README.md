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
    sourceDirs = sourceSets.main.groovy.srcDirs
    format = 'xml'
    includes = ['**/*.java', '**/*.groovy']
}
```

## Configuration

* sourceDirs = Set<File> of directories containing your sources
* _(Optional)_ format = 'html' (default) or 'xml'
* _(Optional)_ reportDir = String or File pointing to a directory to store Cobertura report. Default is `${buildDir}/reports/cobertura`.
* _(Optional)_ instrumentationDir = String or File pointing to where instrumented classes should be put. Default is `${buildDir}/instrmented`.
* _(Optional)_ serFile = String or File where the cobertura.ser file should reside. Default is `${buildDir}/cobertura/cobertura.ser`.
* _(Optional)_ includes = List<String> glob paths to be reported on
* _(Optional)_ excludes = List<String> glob paths to exclude from reporting
* _(Optional)_ ignores = List<String> regexes of classes to exclude from instrumentation

## License
This plugin is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
