package org.gradle.api.plugins.cobertura

import org.gradle.api.plugins.cobertura.util.FunctionalSpec

class CoberturaPluginFunctionalTest extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(CoberturaPlugin)
    }

    def "tasks operation"() {
        given:
        buildFile << """
            apply plugin: "java"

            repositories {
                mavenCentral()
            }
        """
        and:
        file("src/main/java/p/MyClass.java") << "package p;\nclass MyClass{}"

        when:
        run "check"

        then:
        wasExecuted ":testCoberturaReport"
        wasExecuted ":coberturaInstrumentMain"

        when:
        run "check"

        then:
        wasUpToDate ":testCoberturaReport"
        wasUpToDate ":coberturaInstrumentMain"
    }
}
