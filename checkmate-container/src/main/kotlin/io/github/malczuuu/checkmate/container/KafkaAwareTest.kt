package io.github.malczuuu.checkmate.container

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName

/**
 * An interface that provides a shared, pre-configured [KafkaContainer] singleton for
 * integration-test classes.
 *
 * Implementing this interface in a Spring Boot test class automatically makes [kafkaContainer]
 * available as a [ServiceConnection], so Spring auto-configuration wires all Kafka properties from
 * the running container without any extra setup.
 *
 * Usage:
 * ```kotlin
 * @ContainerTest
 * @SpringBootTest
 * class MyKafkaTests : KafkaAwareTest { ... }
 * ```
 */
interface KafkaAwareTest {

  companion object {
    @Container
    @ServiceConnection
    @JvmField
    val kafkaContainer: KafkaContainer =
        KafkaContainer(DockerImageName.parse("apache/kafka:4.2.0"))
            .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")
            .withEnv("KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", "1")
            .withEnv("KAFKA_TRANSACTION_STATE_LOG_MIN_ISR", "1")
  }
}
