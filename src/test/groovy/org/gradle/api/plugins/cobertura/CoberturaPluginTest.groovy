package org.gradle.api.plugins.cobertura

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CoberturaPluginTest extends Specification {
    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply(plugin: CoberturaPlugin)
    }

    def "extensions are installed"() {
        expect:
        project.extensions.getByName("cobertura") instanceof CoberturaPluginExtension
    }
}
