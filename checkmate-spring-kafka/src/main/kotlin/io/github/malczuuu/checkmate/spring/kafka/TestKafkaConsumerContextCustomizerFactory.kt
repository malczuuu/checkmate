package io.github.malczuuu.checkmate.spring.kafka

import io.github.malczuuu.checkmate.annotation.TestListener
import io.github.malczuuu.checkmate.spring.kafka.LocalUtils.isKafkaPresent
import org.springframework.test.context.ContextConfigurationAttributes
import org.springframework.test.context.ContextCustomizer
import org.springframework.test.context.ContextCustomizerFactory

/**
 * SPI implementation of [ContextCustomizerFactory] that detects fields annotated with
 * [TestListener] on the test class and, when any are found, produces a
 * [TestKafkaConsumerInitializer] to register the corresponding consumer beans.
 *
 * Registered via `spring.factories` so that Spring Test picks it up automatically without any
 * explicit test configuration.
 */
internal open class TestKafkaConsumerContextCustomizerFactory : ContextCustomizerFactory {

  /**
   * Inspects [testClass] (and its superclasses) for fields carrying [TestListener]. Returns a
   * [TestKafkaConsumerInitializer] when at least one such field is found, or `null` otherwise.
   *
   * @param testClass the test class being prepared by Spring Test.
   * @param configAttributes context configuration attributes collected for the test class.
   * @return a [ContextCustomizer] that registers consumer beans, or `null` if none are needed.
   */
  override fun createContextCustomizer(
      testClass: Class<*>,
      configAttributes: List<ContextConfigurationAttributes>,
  ): ContextCustomizer? {
    if (!isKafkaPresent()) {
      return null
    }
    if (collectTopicAnnotatedFieldTypes(testClass).isEmpty()) {
      return null
    }
    return TestKafkaConsumerInitializer(testClass)
  }

  private fun collectTopicAnnotatedFieldTypes(testClass: Class<*>): Set<String> {
    val raw = linkedSetOf<String>()
    var clazz: Class<*>? = testClass
    while (clazz != null && clazz != Any::class.java) {
      clazz.declaredFields
          .filter { it.isAnnotationPresent(TestListener::class.java) }
          .map { it.getAnnotation(TestListener::class.java).value }
          .forEach { raw.add(it) }
      clazz = clazz.superclass
    }
    return raw
  }
}
