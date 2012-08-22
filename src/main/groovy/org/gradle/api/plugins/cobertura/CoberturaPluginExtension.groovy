package org.gradle.api.plugins.cobertura

import org.gradle.api.Project

class CoberturaPluginExtension {
    public static final NAME = "cobertura"

    List<String> dirs
    String serFile = "${project.buildDir}/cobertura/cobertura.ser"
    String reportDir = "${project.buildDir}/reports/cobertura"
    String instrumentationDir = "${project.buildDir}/instrumented"
    String format = 'html'
    Set<File> sourceDirs
    List<String> includes = ['**/*.java', '**/*.groovy', '**/*.scala']
    List<String> excludes = ['**/*Test.java', '**/*Test.groovy', '**/*Test.scala']
    List<String> ignores = ['org.apache.tools.*', 'net.sourceforge.cobertura.*']

    Project project

    CoberturaPluginExtension(Project project) {
        this.project = project
        dirs = [project.sourceSets.main.output.classesDir.path]
    }
}
