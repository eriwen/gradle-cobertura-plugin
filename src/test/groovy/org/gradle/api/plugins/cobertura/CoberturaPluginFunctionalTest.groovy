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

            dependencies {
                testCompile "junit:junit:4.10"
            }
        """
        and:
        file("src/main/java/p/MyClass.java") << """
        package p;

        public class MyClass {

            public void someMethod() {
                int a = 1 + 1;
            }

        }
        """

        file("src/test/java/p/MyClassTest.java") << """
        package p;

        public class MyClassTest {

            @org.junit.Test
            public void someMethod() {
                new MyClass().someMethod();
            }

        }
        """

        when:
        run "check"

        then:
        wasExecuted ":coberturaInstrumentMain"
        wasExecuted ":test"
        wasExecuted ":testCoberturaReport"

        when:
        run "check", "-i"

        then:
        wasUpToDate ":coberturaInstrumentMain"
        wasUpToDate ":test"
        wasUpToDate ":testCoberturaReport"
    }
}
