package io.github.malczuuu.checkmate.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ImageNameLoaderTests {

  @Test
  void givenKafkaAndMultiplePlugins_whenGetImageName_thenUsesHighestPriorityPlugin() {
    String image = ImageNameLoader.getImageName("kafka", "apache/kafka:latest");
    assertEquals("custom/kafka:b", image);
  }

  @Test
  void givenNoPlugin_whenGetImageName_thenFallbackToDefault() {
    String defaultValue = "library/unknown:1.0";
    String image = ImageNameLoader.getImageName("unknown-service", defaultValue);
    assertEquals(defaultValue, image);
  }

  @Test
  void givenDifferentServices_whenGetImageName_thenReturnProperValues() {
    String kafka = ImageNameLoader.getImageName("kafka", "kafka:default");
    String postgres = ImageNameLoader.getImageName("postgres", "postgres:default");
    String redis = ImageNameLoader.getImageName("redis", "redis:default");

    assertEquals("custom/kafka:b", kafka);
    assertEquals("custom/postgres:b", postgres);
    assertEquals("custom/redis:c", redis);
  }

  @Test
  void givenCachedValue_whenGetImageNameAgain_thenReturnsCachedValue() {
    String first = ImageNameLoader.getImageName("kafka", "kafka:default");
    String second = ImageNameLoader.getImageName("kafka", "kafka:other");
    assertNotNull(first);
    assertEquals(first, second);
  }
}
