package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class InstrumentCoberturaTask extends DefaultTask {
    @TaskAction
    def run() {
        final def c = project.extensions.cobertura
        ant.taskdef(resource: 'tasks.properties', classpath: project.configurations.cobertura.asPath)
        ant.'cobertura-instrument'(toDir: c.instrumentationDir, datafile: c.datafilePath) {

            // Classes to ignore for instrumentation
            c.ignores.each { ignore(regex: it) }

            c.dirs.each { dir ->
                if (project.file(dir).exists()) {
                    fileset(dir: dir) {
                        include(name: "**/*.class")
                    }
                }
            }

            // Prepend instrumented classes on the runtime classpath
            project.sourceSets.all {
                ext.oldRuntimeClasspath = runtimeClasspath
                runtimeClasspath = project.configurations.cobertura + runtimeClasspath
            }
        }
    }
}
