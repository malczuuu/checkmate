import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("internal.common-convention")
    id("internal.junit-convention")
    id("org.jetbrains.kotlin.jvm")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

kotlin {
    compilerOptions {
        javaParameters = true
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<JavaCompile> {
    options.release = 17
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget("17")
    }
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
    }
}
