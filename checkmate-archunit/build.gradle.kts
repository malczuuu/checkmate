plugins {
    id("internal.kotlin-library-convention")
    id("internal.jacoco-convention")
}

dependencies {
    api(libs.archunit)
    api(libs.junit.jupiter)

    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}
