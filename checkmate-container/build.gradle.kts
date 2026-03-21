plugins {
    id("internal.kotlin-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    compileOnly(libs.junit.jupiter)
    compileOnly(libs.testcontainers.junit.jupiter)
    compileOnly(libs.spring.boot.testcontainers)
    compileOnly(libs.testcontainers.kafka)
    compileOnly(libs.testcontainers.postgresql)

    testImplementation(project(":checkmate-archunit"))
    testImplementation(libs.archunit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.kafka)
    testImplementation(libs.testcontainers.postgresql)
    testRuntimeOnly(libs.junit.platform.launcher)
}
