package org.gradle.api.plugins.cobertura.tasks

import org.gradle.api.tasks.*
import org.gradle.api.file.FileCollection

class CoberturaTask extends SourceTask {

    @Input
    String format

    @OutputDirectory
    File reportDir

    @InputFile
    File serFile

    @TaskAction
    def run() {
        def source = getSource()
        if (!source.empty) {
            ant.'cobertura-report'(format: getFormat(), destdir: getReportDir(), datafile: getSerFile()) {
                getSource().addToAntBuilder(delegate, "fileset", FileCollection.AntType.FileSet)
            }
        } else {
            logger.warn 'Cobertura cannot run becuase no source directories were found.'
        }
    }
}
