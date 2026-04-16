import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.JUnit
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext")
}

idea {
    project {
        settings {
            runConfigurations {
                create<Gradle>("Build [checkmate]") {
                    taskNames = listOf("spotlessApply build")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("Build [checkmate|containers]") {
                    taskNames = listOf("spotlessApply build -Pcontainers.enabled")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("Test [checkmate]") {
                    taskNames = listOf("test")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("Test [checkmate|containers]") {
                    taskNames = listOf("test -Pcontainers.enabled")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<JUnit>("JUnit [checkmate]") {
                    moduleName = "checkmate.test"
                    workingDirectory = rootProject.rootDir.absolutePath
                    packageName = "io.github.malczuuu.checkmate"
                }
            }
        }
    }
}
