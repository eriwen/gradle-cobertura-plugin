package org.gradle.api.plugins.cobertura

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.cobertura.tasks.*

class CoberturaPlugin implements Plugin<Project> {

    void apply(final Project project) {
        project.extensions.create(CoberturaPluginExtension.NAME, CoberturaPluginExtension, project)

        project.configurations {
            cobertura
        }
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            cobertura 'net.sourceforge.cobertura:cobertura:1.9.4.1'
            cobertura files(coberturaExtension.instrumentationDir)
        }

        project.tasks.findByName('test').each {
            it.configure {
                dependsOn 'instrumentCobertura'
                systemProperties.put('net.sourceforge.cobertura.datafile', project.extensions.cobertura.coverageDatafile)
//                classpath += project.configurations['cobertura']
//                fixTestClasspath(project, it)
            }
        }

        check.dependsOn 'cobertura'

        applyTasks(project)
    }

    void applyTasks(final Project project) {
        project.task('instrumentCobertura', type: InstrumentCoberturaTask) {}
        project.task('cobertura', type: CoberturaTask) {}
    }
}
