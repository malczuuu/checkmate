package io.github.malczuuu.checkmate.spring.kafka

import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextCustomizer
import org.springframework.test.context.MergedContextConfiguration

/**
 * [ContextCustomizer] that registers [TestKafkaConsumer] bean definitions for every
 * `TestListener`-annotated field found on the test class.
 *
 * Produced by [TestKafkaConsumerContextCustomizerFactory] and keyed on [testClass] so that two
 * customizers for the same test class are considered equal and deduplicated by Spring Test.
 *
 * @param testClass the test class whose annotated fields drive bean registration.
 */
internal class TestKafkaConsumerInitializer(private val testClass: Class<*>) : ContextCustomizer {

  /**
   * Delegates to [TestKafkaConsumerRegistrar] to register one [TestKafkaConsumer] bean definition
   * per distinct topic found on the test class.
   *
   * @param context the configurable application context being customised.
   * @param mergedConfig merged context configuration for the current test.
   */
  override fun customizeContext(
      context: ConfigurableApplicationContext,
      mergedConfig: MergedContextConfiguration,
  ) {
    val registry = context.beanFactory as BeanDefinitionRegistry
    TestKafkaConsumerRegistrar(testClass, context.environment, context.beanFactory)
        .registerBeanDefinitions(registry)
  }

  /**
   * Two [TestKafkaConsumerInitializer] instances are equal when they were created for the same
   * [testClass], allowing Spring Test to deduplicate context customizers across test hierarchies.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other !is TestKafkaConsumerInitializer) {
      return false
    }
    return testClass == other.testClass
  }

  /** Returns a hash code based solely on [testClass]. */
  override fun hashCode(): Int = testClass.hashCode()
}
