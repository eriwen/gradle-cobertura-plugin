package org.gradle.api.plugins.cobertura

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.cobertura.tasks.*
import org.gradle.api.tasks.testing.Test

class CoberturaPlugin implements Plugin<Project> {

    void apply(final Project project) {
        project.apply(plugin: 'java')
        project.extensions.create(CoberturaPluginExtension.NAME, CoberturaPluginExtension, project)

        project.configurations {
            cobertura
        }
        project.dependencies {
            cobertura 'net.sourceforge.cobertura:cobertura:1.9.4.1'
            cobertura project.files(project.extensions.cobertura.instrumentationDir)
        }

        project.tasks.findAll { it instanceof Test }.each {
            it.configure {
                dependsOn 'instrumentCobertura'
                systemProperties.put('net.sourceforge.cobertura.datafilePath', project.extensions.cobertura.datafilePath)
            }
        }

        applyTasks(project)

        project.tasks.findByName('check').dependsOn 'cobertura'
    }

    void applyTasks(final Project project) {
        project.task('instrumentCobertura', type: InstrumentCoberturaTask, group: 'Verification',
                description: 'Instruments classes for Cobertura coverage reports') {
            outputs.files project.extensions.cobertura.instrumentationDir, project.extensions.cobertura.datafilePath
        }
        project.task('cobertura', type: CoberturaTask, dependsOn: ['instrumentCobertura', 'test'],
                group: 'Verification', description: 'Generate Cobertura coverage report') {
            inputs.file project.file(project.extensions.cobertura.datafilePath)
            outputs.dir project.file(project.extensions.cobertura.reportDir)
            doLast {
                project.sourceSets.all {
                    runtimeClasspath = ext.oldRuntimeClasspath
                }
            }
        }
    }
}
