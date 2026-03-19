package io.github.malczuuu.checkmate.annotation

import org.junit.jupiter.api.Tag

@Tag(ContainerTest.TAG_NAME)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ContainerTest {

    companion object {
        const val TAG_NAME = "testcontainers"
    }
}
