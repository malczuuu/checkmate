package io.github.malczuuu.checkmate.spring.kafka;

class LocalUtils {

  /**
   * Checks whether the Kafka client library is present on the classpath by attempting to load a
   * core class.
   *
   * @return {@code true} if the class is found, or {@code false} if a {@code
   *     ClassNotFoundException} is thrown
   */
  static boolean isKafkaPresent() {
    try {
      Class.forName("org.apache.kafka.clients.consumer.KafkaConsumer");
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }
}
