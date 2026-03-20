package io.github.malczuuu.checkmate.spring.kafka

import java.time.Duration
import java.util.Collections
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.context.SmartLifecycle

/**
 * Default implementation of [TestKafkaConsumer] that wraps a raw [KafkaConsumer] and integrates
 * with the Spring [SmartLifecycle] to subscribe/unsubscribe automatically when the application
 * context starts and stops.
 *
 * All operations are guarded by a [ReentrantLock] to make them safe for concurrent access from test
 * threads and the lifecycle callbacks.
 *
 * @param consumerSupplier factory that creates the underlying [KafkaConsumer] on [start].
 * @param topic Kafka topic this consumer subscribes to.
 */
internal class DefaultTestKafkaConsumer(
    private val consumerSupplier: () -> KafkaConsumer<String, String>,
    private val topic: String,
) : TestKafkaConsumer, SmartLifecycle {

  companion object {
    private val log = LoggerFactory.getLogger(DefaultTestKafkaConsumer::class.java)
  }

  private val lock = ReentrantLock()

  private var consumer: KafkaConsumer<String, String>? = null

  /**
   * Polls the underlying [KafkaConsumer] once within [timeout] and returns an unmodifiable list of
   * received records.
   *
   * @param timeout maximum time to block waiting for records.
   * @return immutable list of [ConsumerRecord]s received during the poll; empty if none arrived.
   * @throws IllegalStateException if the consumer has not been started yet.
   */
  override fun poll(timeout: Duration): List<ConsumerRecord<String, String>> =
      lock.withLock {
        val consumer = this.consumer ?: throw IllegalStateException("consumer is not initialized")
        val result = mutableListOf<ConsumerRecord<String, String>>()
        consumer.poll(timeout).forEach { r -> result.add(r) }
        return Collections.unmodifiableList(result)
      }

  /**
   * Drains the topic by repeatedly polling until no records are returned or [timeout] elapses.
   *
   * @param timeout maximum total time to spend draining buffered records.
   */
  override fun clear(timeout: Duration): Unit =
      lock.withLock {
        consumer?.let {
          val deadlineMillis = System.currentTimeMillis() + timeout.toMillis()
          while (
              System.currentTimeMillis() < deadlineMillis &&
                  !it.poll(Duration.ofMillis(100)).isEmpty
          ) {
            // ignored - just keep polling until timeout or no more records
          }
        }
      }

  override fun start(): Unit =
      lock.withLock {
        log.info("Creating and subscribing KafkaConsumer on topic={}", topic)
        consumer = consumerSupplier()
        consumer?.subscribe(listOf(topic))
      }

  override fun stop(): Unit =
      lock.withLock {
        log.info("Closing KafkaConsumer on topic={}", topic)
        consumer?.close()
        consumer = null
      }

  override fun isRunning(): Boolean = lock.withLock { consumer != null }
}
