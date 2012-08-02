package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class InstrumentCoberturaTask extends DefaultTask {
    @TaskAction
    def run() {
        String instrumentationPath = 'TODO'
        String dataFilePath = 'ALSOTODO'
        ant.taskdef(resource: 'tasks.properties', classpath: target.configurations.cobertura.asPath)
        ant.'cobertura-instrument'(toDir: instrumentationPath, datafile: dataFilePath) {
            if (target.sourceSets.main.output.classesDir.exists()) {
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
