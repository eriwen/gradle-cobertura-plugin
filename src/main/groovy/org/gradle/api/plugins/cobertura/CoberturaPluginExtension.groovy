package org.gradle.api.plugins.cobertura

import org.gradle.api.Project

class CoberturaPluginExtension {
    public static final NAME = "cobertura"

    List<String> coverageDirs
    File coverageDatafile
    File coverageReportDir
    String coverageFormat = 'html'
    Set<File> coverageSourceDirs
    List<String> coverageIncludes
    List<String> coverageExcludes
    List<String> coverageIgnores

    Project project

    CoberturaPluginExtension(Project project) {
        this.project = project
        coverageDirs = [ project.sourceSets.main.output.classesDir.path ]
        coverageDatafile = new File("${project.buildDir.path}/cobertura", 'cobertura.ser')
        coverageReportDir = new File("${project.reporting.baseDir.path}/cobertura")
        coverageSourceDirs = project.sourceSets.main.java.srcDirs
    }
}
