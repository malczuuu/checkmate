plugins {
    id("internal.kotlin-spring-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    annotationProcessor(libs.spring.boot.configuration.processor)

    api(project(":checkmate-annotation"))
    api(project(":checkmate-container"))

    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.kafka.clients)
    compileOnly(libs.spring.boot.starter.kafka)
    compileOnly(libs.spring.boot.starter.test)
    compileOnly(libs.spring.boot.testcontainers)
    compileOnly(libs.testcontainers.kafka)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlin.test.junit5)

    testImplementation(project(":checkmate-archunit"))
    testImplementation(libs.archunit)
    testImplementation(libs.jackson.module.kotlin)
    testImplementation(libs.kafka.clients)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.kafka)
    testImplementation(libs.spring.boot.starter.jackson)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.kafka)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.kotlin.test.junit5)

    testRuntimeOnly(libs.junit.platform.launcher)
}
