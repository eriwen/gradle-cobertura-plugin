package org.gradle.api.plugins.cobertura

import org.gradle.api.plugins.cobertura.util.FunctionalSpec

class CoberturaPluginFunctionalTest extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(CoberturaPlugin)
    }

    def "tasks operation"() {
        given:
        buildFile << """
            sourceSets.main.java.srcDirs = ['src/main/java']

            cobertura {
                sourceDirs = sourceSets.main.java.srcDirs
            }
        """
        and:
        file("src/main/java/p/MyClass.java") << "package p;\nclass MyClass{}"

        when:
        run "test"

        then:
        wasExecuted ":instrumentCobertura"
        wasExecuted ":cobertura"
    }
}
