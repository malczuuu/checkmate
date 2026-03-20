package io.github.malczuuu.checkmate.spring.kafka

import io.github.malczuuu.checkmate.annotation.TestListener
import java.lang.reflect.Field
import java.util.UUID
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.getBean
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.boot.kafka.autoconfigure.KafkaConnectionDetails
import org.springframework.core.env.Environment

/**
 * Registers [TestKafkaConsumer] bean definitions in the given [BeanDefinitionRegistry] for every
 * unique Kafka topic referenced by [TestListener]-annotated fields on [testClass] and its
 * superclasses.
 *
 * Each consumer bean is named `testKafkaConsumer[<rawTopicValue>]` and qualified with
 * [TestListener] so it can be looked up by topic name.
 *
 * @param testClass the test class whose annotated fields determine which topics need consumers.
 * @param environment used to resolve property placeholders in topic names.
 * @param beanFactory used to obtain [KafkaConnectionDetails] when instantiating the underlying
 *   [KafkaConsumer].
 */
internal class TestKafkaConsumerRegistrar(
    private val testClass: Class<*>,
    private val environment: Environment,
    private val beanFactory: BeanFactory,
) {

  /**
   * Iterates over all declared fields of [testClass] and its superclasses, registering a
   * [TestKafkaConsumer] bean definition for each field annotated with [TestListener] (skipping
   * duplicates).
   *
   * @param registry the bean definition registry to register consumer definitions into.
   */
  fun registerBeanDefinitions(registry: BeanDefinitionRegistry) {
    var clazz: Class<*>? = testClass
    while (clazz != null && clazz != Any::class.java) {
      for (field in clazz.declaredFields) {
        registerBeanIfAble(field, registry)
      }
      clazz = clazz.superclass
    }
  }

  private fun registerBeanIfAble(field: Field, registry: BeanDefinitionRegistry) {
    val annotation = field.getAnnotation(TestListener::class.java)
    if (annotation != null) {
      val rawValue = annotation.value
      val resolvedTopic = environment.resolveRequiredPlaceholders(rawValue)
      val beanName = "testKafkaConsumer[$rawValue]"
      if (!registry.containsBeanDefinition(beanName)) {
        registry.registerBeanDefinition(beanName, buildDefinition(rawValue, resolvedTopic))
      }
    }
  }

  private fun buildDefinition(rawValue: String, resolvedTopic: String): RootBeanDefinition {
    val definition = RootBeanDefinition(TestKafkaConsumer::class.java)
    definition.setInstanceSupplier {
      val connectionDetails = beanFactory.getBean<KafkaConnectionDetails>()
      val bootstrapServers = connectionDetails.consumer.bootstrapServers.joinToString(",")
      createClient(bootstrapServers, resolvedTopic)
    }
    definition.addQualifier(AutowireCandidateQualifier(TestListener::class.java, rawValue))
    return definition
  }

  private fun createClient(bootstrapServers: String, resolvedTopic: String): TestKafkaConsumer {
    val props =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to getUniqueGroupId(resolvedTopic),
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        )
    val consumer = KafkaConsumer(props, StringDeserializer(), StringDeserializer())
    return DefaultTestKafkaConsumer({ consumer }, resolvedTopic)
  }

  private fun getUniqueGroupId(topic: String): String = "test-consumer.$topic.${UUID.randomUUID()}"
}
