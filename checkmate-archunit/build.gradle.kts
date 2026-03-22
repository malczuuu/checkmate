plugins {
    id("internal.errorprone-convention")
    id("internal.java-library-convention")
    id("internal.jacoco-convention")
    id("internal.publishing-convention")
}

dependencies {
    compileOnly(libs.archunit)
    compileOnly(libs.junit.jupiter)

    testImplementation(libs.archunit)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
}
