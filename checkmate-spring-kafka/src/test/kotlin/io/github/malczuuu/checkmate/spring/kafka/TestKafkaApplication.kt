package io.github.malczuuu.checkmate.spring.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate

@EnableKafka
@SpringBootApplication
internal class TestKafkaApplication {

  @Autowired private lateinit var kafkaTemplate: KafkaTemplate<String, String>

  @Bean
  fun topics(): KafkaAdmin.NewTopics =
      KafkaAdmin.NewTopics(
          TopicBuilder.name(TOPIC_IN).partitions(1).replicas(1).build(),
          TopicBuilder.name(TOPIC_OUT).partitions(1).replicas(1).build(),
      )

  @KafkaListener(topics = [TOPIC_IN], groupId = "testkit-app")
  fun onMessage(message: String) {
    kafkaTemplate.send(TOPIC_OUT, "forwarded-$message")
  }

  companion object {
    const val TOPIC_IN = "testkit-topic-in"
    const val TOPIC_OUT = "testkit-topic-out"
  }
}
