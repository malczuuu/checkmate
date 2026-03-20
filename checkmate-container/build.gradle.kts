plugins {
    id("internal.kotlin-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    api(libs.junit.jupiter)

    api(libs.testcontainers.junit.jupiter)
    compileOnly(libs.testcontainers.kafka)
    compileOnly(libs.testcontainers.postgresql)

    compileOnly(libs.spring.boot.testcontainers)

    testImplementation(project(":checkmate-archunit"))

    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}
