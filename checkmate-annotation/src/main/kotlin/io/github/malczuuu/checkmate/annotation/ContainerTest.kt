package io.github.malczuuu.checkmate.annotation

import org.junit.jupiter.api.Tag

/**
 * Marks a test class as container-based, applying the [TAG_NAME] JUnit tag so that
 * Testcontainers-backed tests can be selectively included or excluded from a build.
 *
 * @see TAG_NAME
 */
@Tag(ContainerTest.TAG_NAME)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ContainerTest {

  companion object {
    const val TAG_NAME = "testcontainers"
  }
}
