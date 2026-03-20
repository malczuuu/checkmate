package io.github.malczuuu.checkmate.spring.kafka

import java.time.Duration
import org.awaitility.Awaitility.await
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.kafka.config.KafkaListenerEndpointRegistry

/**
 * Spring Boot auto-configuration applied during integration tests to ensure that all Kafka listener
 * containers have received their partition assignments before any test method runs.
 *
 * The configuration registers an [ApplicationListener] that, upon a [ContextRefreshedEvent], blocks
 * until every listener container managed by the [KafkaListenerEndpointRegistry] reports that it is
 * running and has at least one assigned partition.
 */
@AutoConfiguration
class KafkaTestAutoConfiguration {

  /**
   * Creates an [ApplicationListener] that waits (up to 30 seconds) for all Kafka listener
   * containers to receive their partition assignments after the context is refreshed.
   *
   * Only registered when a [KafkaListenerEndpointRegistry] bean is present in the context.
   *
   * @param registry the registry holding all Kafka listener containers.
   * @return an [ApplicationListener] for [ContextRefreshedEvent] that blocks until assignment is
   *   complete.
   */
  @Bean
  @ConditionalOnBean(KafkaListenerEndpointRegistry::class)
  fun kafkaAssignmentAwaiter(
      registry: KafkaListenerEndpointRegistry
  ): ApplicationListener<ContextRefreshedEvent> = ApplicationListener { registry.awaitAssignment() }

  private fun KafkaListenerEndpointRegistry.awaitAssignment() {
    await().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofMillis(200)).until {
      hasAllPartitionsAssigned()
    }
  }

  private fun KafkaListenerEndpointRegistry.hasAllPartitionsAssigned(): Boolean =
      allListenerContainers.all { container ->
        val partitions = container.assignedPartitions
        container.isRunning && !partitions.isNullOrEmpty()
      }
}
