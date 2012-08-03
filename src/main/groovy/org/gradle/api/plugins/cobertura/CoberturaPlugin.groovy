package org.gradle.api.plugins.cobertura

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.cobertura.tasks.*

class CoberturaPlugin implements Plugin<Project> {

    void apply(final Project project) {
        project.apply(plugin: 'java')
        project.extensions.create(CoberturaPluginExtension.NAME, CoberturaPluginExtension, project)

        project.configurations {
            cobertura
        }
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            cobertura 'net.sourceforge.cobertura:cobertura:1.9.4.1'
            cobertura project.files(project.extensions.cobertura.instrumentationDir)
        }

        project.tasks.findByName('test').configure {
            dependsOn 'cobertura'
            systemProperties.put('net.sourceforge.cobertura.datafile', project.extensions.cobertura.datafile)
        }

        applyTasks(project)

        project.tasks.findByName('check').dependsOn 'cobertura'
    }

    void applyTasks(final Project project) {
        project.task('instrumentCobertura', type: InstrumentCoberturaTask, group: 'Verification',
                description: 'Instruments classes for Cobertura coverage reports') {}
        project.task('cobertura', type: CoberturaTask, dependsOn: 'instrumentCobertura',
                group: 'Verification', description: 'Generate Cobertura coverage report') {}
    }
}
