package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.*

class InstrumentCoberturaTask extends SourceTask {

    @InputFiles
    FileCollection coberturaClasspath

    @OutputDirectory
    File classesDir

    @OutputFile
    File serFile

    @Input
    List<String> ignores

    @TaskAction
    def run() {
        ant.taskdef(resource: 'tasks.properties', classpath: getCoberturaClasspath().asPath)
        ant.'cobertura-instrument'(toDir: getClassesDir(), datafile: getSerFile()) {
            getIgnores().each { ignore(regex: it) }
            getSourceClassFiles().addToAntBuilder(delegate, "fileset", FileCollection.AntType.FileSet)
        }
    }

    protected FileCollection getSourceClassFiles() {
        getSource().filter { File it -> it.name.endsWith(".class") }
    }

    FileCollection getInstrumentedClassFiles() {
        project.files({ getClassesDir() }) {
            builtBy this
        }
    }
}
