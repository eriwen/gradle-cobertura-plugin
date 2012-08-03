package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class CoberturaTask extends DefaultTask {
    @TaskAction
    def run() {
        final def c = project.extensions.cobertura
        if (c.sourceDirs) {
            ant.'cobertura-report'(format: c.format, destdir: c.reportDir, datafile: c.datafile) {
                c.sourceDirs.each { dir ->
                    if (project.file(dir).exists()) {
                        fileset(dir: dir) {
                            c.includes.each { include(name: it) }
                            c.excludes.each { exclude(name: it) }
                        }
                    }
                }
            }
        } else {
            logger.warn 'Cobertura cannot run becuase no source directories were found.'
        }
    }
}
