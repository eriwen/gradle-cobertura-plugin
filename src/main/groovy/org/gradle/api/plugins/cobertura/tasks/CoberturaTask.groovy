package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class CoberturaTask extends DefaultTask {
    String group = 'Verification'
    String description = 'Generate Cobertura coverage report'
//    Set<Task> dependencies = [InstrumentCoberturaTask, TestTask] as Set<Task>

    @TaskAction
    def run() {
        logger.info "Writing cobertura report"
        if (project.extensions.cobertura.srcDirs) {
            logger.info "Creating Cobertura reports for " + project.extensions.cobertura.srcDirs
            logger.info "Reports will be located in the directory " + project.extensions.cobertura.reportDir
            ant.'cobertura-report'(destdir: project.extensions.cobertura.reportDir,
                    datafile: project.extensions.cobertura.dataFile){
                project.extensions.cobertura.srcDirs.each {dir->
                    fileset(dir: dir){
                        include(name: "**/*.groovy")
                    }
                }
            }
        } else {
            logger.warn "Cobertura cannot run becuase no source directories were found."
        }
    }
}
