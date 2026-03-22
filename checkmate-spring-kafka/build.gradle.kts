plugins {
    id("internal.errorprone-convention")
    id("internal.java-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    annotationProcessor(libs.spring.boot.configuration.processor)

    api(project(":checkmate-annotation"))
    api(project(":checkmate-container"))

    compileOnly(libs.kafka.clients)
    compileOnly(libs.spring.boot.starter.kafka)
    compileOnly(libs.spring.boot.starter.test)
    compileOnly(libs.spring.boot.testcontainers)
    compileOnly(libs.testcontainers.kafka)

    testImplementation(project(":checkmate-archunit"))
    testImplementation(libs.archunit)
    testImplementation(libs.kafka.clients)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.kafka)
    testImplementation(libs.spring.boot.starter.jackson)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.kafka)

    testRuntimeOnly(libs.junit.platform.launcher)

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
}
