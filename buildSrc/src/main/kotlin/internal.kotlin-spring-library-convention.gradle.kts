plugins {
    id("internal.kotlin-library-convention")
    id("org.jetbrains.kotlin.plugin.spring")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
