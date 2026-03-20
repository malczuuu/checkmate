plugins {
    id("internal.kotlin-library-convention")
    id("internal.jacoco-convention")
}

dependencies {
    api(libs.junit.jupiter)

    testImplementation(project(":checkmate-archunit"))

    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}
