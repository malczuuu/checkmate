package io.github.malczuuu.checkmate.spring.kafka

/** Utility methods for local use within this module. */
internal object LocalUtils {

  /**
   * Checks whether the Kafka client library is present on the classpath by attempting to load a
   * core class.
   *
   * @returns `true` if the class is found, or `false` if a `ClassNotFoundException` is thrown
   */
  fun isKafkaPresent(): Boolean {
    try {
      Class.forName("org.apache.kafka.clients.consumer.KafkaConsumer")
      return true
    } catch (_: ClassNotFoundException) {
      return false
    }
  }
}
