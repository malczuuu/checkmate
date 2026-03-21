plugins {
    id("internal.kotlin-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    compileOnly(libs.junit.jupiter)

    testImplementation(project(":checkmate-archunit"))
    testImplementation(libs.archunit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}
