plugins {
    id("internal.kotlin-spring-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    annotationProcessor(libs.spring.boot.configuration.processor)

    api(project(":checkmate-annotation"))
    api(project(":checkmate-container"))

    api(libs.jackson.module.kotlin)

    api(libs.kafka.clients)

    api(libs.kotlin.reflect)
    api(libs.kotlin.test.junit5)

    api(libs.spring.boot.starter.kafka)
    api(libs.spring.boot.starter.test)
    api(libs.spring.boot.testcontainers)

    api(libs.testcontainers.kafka)

    testImplementation(project(":checkmate-archunit"))

    testImplementation(libs.spring.boot.starter.jackson)

    testRuntimeOnly(libs.junit.platform.launcher)
}
