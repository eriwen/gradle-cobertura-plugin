package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class InstrumentCoberturaTask extends DefaultTask {
    @TaskAction
    def run() {
        ant.taskdef(resource: 'tasks.properties', classpath: project.configurations.cobertura.asPath)
        ant.'cobertura-instrument'(toDir: project.extensions.cobertura.instrumentationDir,
                datafile: project.extensions.cobertura.datafile) {
            // TODO: ignore(regex: "**/*.foo")
            if (project.sourceSets.main.output.classesDir.exists()) {
                fileset(dir: project.sourceSets.main.output.classesDir) {
                    include(name: "**/*.class")
                }
            }

            // FIXME: This is causing Spring unit tests to fail.
            project.sourceSets.all {
                runtimeClasspath = project.configurations.cobertura + runtimeClasspath
            }
        }
    }
}
