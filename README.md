# Gradle Cobertura Plugin
Produces code coverage reports for your JVM-based projects using [Cobertura](http://cobertura.sourceforge.net/)

## Quick Start

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.mapvine:gradle-cobertura-plugin:0.1.1'
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
* _(Optional)_ includes = List<String> glob paths to be analyzed
* _(Optional)_ excludes = List<String> glob paths to exclude from analysis

## License
This plugin is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
