package io.github.malczuuu.checkmate.spring.kafka

import io.github.malczuuu.checkmate.annotation.TestListener
import io.github.malczuuu.checkmate.spring.kafka.LocalUtils.isKafkaPresent
import org.springframework.beans.factory.getBean
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener

/**
 * [TestExecutionListener] that injects [TestKafkaConsumer] beans into fields annotated with
 * [TestListener] before each test instance is used.
 *
 * Walks the full class hierarchy of the test instance so that annotated fields declared in
 * superclasses are also populated.
 *
 * Registered via `spring.factories`.
 */
internal open class TestKafkaConsumerInjector : TestExecutionListener {

  /**
   * Scans all declared fields of the test instance (including inherited ones) and injects the
   * matching [TestKafkaConsumer] bean from the application context for every field annotated with
   * [TestListener].
   *
   * @param testContext the current Spring test context providing the test instance and application
   *   context.
   */
  override fun prepareTestInstance(testContext: TestContext) {
    if (!isKafkaPresent()) {
      return
    }
    val testInstance = testContext.testInstance
    val applicationContext = testContext.applicationContext

    var clazz: Class<*>? = testInstance::class.java
    while (clazz != null && clazz != Any::class.java) {
      for (field in clazz.declaredFields) {
        val annotation = field.getAnnotation(TestListener::class.java)
        if (annotation != null && field.type == TestKafkaConsumer::class.java) {
          val beanName = "testKafkaConsumer[${annotation.value}]"
          val consumer = applicationContext.getBean<TestKafkaConsumer>(beanName)
          field.isAccessible = true
          field.set(testInstance, consumer)
        }
      }
      clazz = clazz.superclass
    }
  }
}
