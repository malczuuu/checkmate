import com.diffplug.spotless.LineEnding

plugins {
    id("internal.errorprone-convention")
    id("internal.idea-convention")
    id("internal.jacoco-convention")
    id("internal.java-library-convention")
    id("internal.publishing-convention")
    alias(libs.plugins.spotless)
}

dependencies {
    annotationProcessor(libs.spring.boot.configuration.processor)

    api(libs.jspecify)

    compileOnly(libs.archunit)
    compileOnly(libs.junit.jupiter)
    compileOnly(libs.kafka.clients)
    compileOnly(libs.spring.boot.starter.kafka)
    compileOnly(libs.spring.boot.starter.test)
    compileOnly(libs.spring.boot.testcontainers)
    compileOnly(libs.testcontainers.junit.jupiter)
    compileOnly(libs.testcontainers.kafka)
    compileOnly(libs.testcontainers.mongodb)
    compileOnly(libs.testcontainers.postgresql)

    testImplementation(libs.archunit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kafka.clients)
    testImplementation(libs.spring.boot.starter.jackson)
    testImplementation(libs.spring.boot.starter.kafka)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.kafka)
    testImplementation(libs.testcontainers.postgresql)

    testRuntimeOnly(libs.junit.platform.launcher)

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
}

spotless {
    java {
        target("**/src/**/*.java")

        googleJavaFormat("1.35.0")
        forbidWildcardImports()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlin {
        target("**/src/**/*.kt")

        ktfmt("0.61").googleStyle().configure {
            it.setMaxWidth(100)
            it.setContinuationIndent(4)
            it.setRemoveUnusedImports(true)
        }
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlinGradle {
        target("*.gradle.kts", "buildSrc/*.gradle.kts", "buildSrc/src/**/*.gradle.kts")
        targetExclude("**/build/**")

        ktlint("1.8.0").editorConfigOverride(mapOf("max_line_length" to "120"))
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format("yaml") {
        target("**/*.yml", "**/*.yaml")
        targetExclude("**/build/**")

        trimTrailingWhitespace()
        leadingTabsToSpaces(2)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format("misc") {
        target("**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }
}

defaultTasks("spotlessApply", "build")
