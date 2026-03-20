package io.github.malczuuu.checkmate.spring.kafka

import java.time.Duration
import org.apache.kafka.clients.consumer.ConsumerRecord

/**
 * A lightweight Kafka consumer intended for use inside Spring integration tests.
 *
 * An instance is automatically registered in the Spring application context and injected into
 * fields annotated with `TestListener`. The consumer starts and stops together with the application
 * context lifecycle.
 */
interface TestKafkaConsumer {

  /**
   * Polls the Kafka topic once and returns all records received within [timeout].
   *
   * @param timeout maximum time to wait for records in a single poll call.
   * @return an immutable list of received [ConsumerRecord]s; empty if none arrived within the
   *   timeout.
   */
  fun poll(timeout: Duration): List<ConsumerRecord<String, String>>

  /**
   * Drains any buffered records using a default timeout of 300 ms.
   *
   * Delegates to [clear] with [Duration.ofMillis(300)][java.time.Duration.ofMillis].
   */
  fun clear() = clear(Duration.ofMillis(300))

  /**
   * Drains all buffered records by repeatedly polling until no more records arrive or [timeout]
   * elapses.
   *
   * @param timeout maximum total time to spend draining the topic.
   */
  fun clear(timeout: Duration)
}
