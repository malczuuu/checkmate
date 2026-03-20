package io.github.malczuuu.checkmate.annotation

/**
 * Marks a field in a test class as a consumer that should be wired to the messaging destination
 * identified by [value].
 *
 * The framework detects this annotation during Spring test-context customization and automatically
 * registers a dedicated consumer bean for the resolved destination, then injects it into the
 * annotated field before each test.
 *
 * @property value The destination name (e.g. a topic or queue) or a property placeholder resolvable
 *   via the Spring `Environment`.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class TestListener(val value: String)
