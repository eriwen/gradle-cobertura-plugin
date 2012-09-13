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

            repositories {
                mavenCentral()
            }
            cobertura {
                sourceDirs = sourceSets.main.java.srcDirs
            }
        """
        and:
        file("src/main/java/p/MyClass.java") << "package p;\nclass MyClass{}"

        when:
        run "check"

        then:
        wasExecuted ":instrumentCobertura"
        wasExecuted ":cobertura"

        when:
        run "cobertura"

        then:
        wasUpToDate ":instrumentCobertura"
        wasUpToDate ":cobertura"
    }

    def "handles interfaces properly"() {
        given:
        buildFile << """
            apply plugin: "groovy"

            repositories {
                mavenCentral()
            }

            dependencies {
                testCompile "junit:junit:4.10"
                groovy localGroovy()
            }
        """

        and:
        file("src/main/groovy/p/MyInterface.groovy") << """
        package p
        interface MyInterface {
            Boolean passesTest()
        }
        """

        and:
        file("src/main/groovy/p/MyClass.groovy") << """
        package p
        class MyClass implements MyInterface {
            Boolean passesTest() {
               return (Math.random() <= 1)
            }
        }
        """

        file("src/test/groovy/p/MyClassTest.groovy") << """
            package p
            import org.junit.Test

            class MyClassTest {

                @Test
                void passesTest() {
                    def myClass = new MyClass()
                    assert myClass.passesTest() == true
                }
            }
        """

        when:
        run "test"

        then:
        notThrown ClassNotFoundException
    }
}
