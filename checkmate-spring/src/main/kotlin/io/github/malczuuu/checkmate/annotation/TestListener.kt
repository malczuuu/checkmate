package io.github.malczuuu.checkmate.annotation

/**
 * Marks a [io.github.malczuuu.checkmate.kafka.TestKafkaConsumer] field with the Kafka topic it
 * should consume from and allows injecting it in test classes.
 *
 * Example usage in a test class:
 * ```
 * @TestListener("\${checkmate.kafka.topic.player-events}")
 * private lateinit var kafkaConsumer: TestKafkaConsumer
 * ```
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class TestListener(val value: String)
