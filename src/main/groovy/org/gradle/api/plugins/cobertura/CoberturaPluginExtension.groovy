package org.gradle.api.plugins.cobertura

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.cobertura.tasks.CoberturaTask
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.testing.Test
import java.util.concurrent.Callable

class CoberturaPluginExtension {
    public static final NAME = "cobertura"

    FileCollection classpath

    File reportsDir

    String format = 'html'
    List<String> includes = ['**/*.java', '**/*.groovy', '**/*.scala']
    List<String> excludes = ['**/*Test.java', '**/*Test.groovy', '**/*Test.scala']

    List<String> ignores = ['org.apache.tools.*', 'net.sourceforge.cobertura.*']

    private final Project project

    CoberturaPluginExtension(Project project) {
        this.project = project
    }

    void addCoverage(Test testTask, SourceSet sourceSet) {
        CoberturaSourceSetExtension sourceSetExtension = sourceSet.cobertura

        def deferredProperty = new LazyString("${->sourceSetExtension.serFile}")

        testTask.systemProperties.put('net.sourceforge.cobertura.datafile', deferredProperty)
        testTask.conventionMapping.with {
            map("classpath") { sourceSetExtension.runtimeClasspath }
            map("testClassesDir") { sourceSetExtension.classesDir }
        }

        def coberturaTask = project.tasks.add("${testTask.name}CoberturaReport", CoberturaTask)
        coberturaTask.conventionMapping.with {
            map("source") { sourceSet.allSource }
            map("includes") { getIncludes() as Set }
            map("excludes") { getExcludes() as Set }
            map("format") { getFormat() }
            map("reportDir") { new File(getReportsDir(), sourceSet.name) }
            map("serFile") { sourceSetExtension.getSerFile() }
        }
    }

    private static class LazyString implements Serializable {
        private value

        LazyString(value) {
            this.value = value
        }

        @Override
        String toString() {
            value.toString()
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(toString())
        }

        private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
            value = input.readObject()
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            LazyString that = (LazyString) o

            if (value != that.value) return false

            return true
        }

        int hashCode() {
            return (value != null ? value.hashCode() : 0)
        }
    }

}
