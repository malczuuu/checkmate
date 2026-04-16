package io.github.malczuuu.checkmate.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ImageNameLoaderTests {

  @Test
  void givenServiceWithMultiplePlugins_whenGetImageName_thenUsesHighestPriorityPlugin() {
    String image = ImageNameLoader.getImageName("svc-alpha", "default/alpha:latest");
    assertEquals("custom/alpha:b", image);
  }

  @Test
  void givenNoPlugin_whenGetImageName_thenFallbackToDefault() {
    String defaultValue = "library/unknown:1.0";
    String image = ImageNameLoader.getImageName("unknown-service", defaultValue);
    assertEquals(defaultValue, image);
  }

  @Test
  void givenDifferentServices_whenGetImageName_thenReturnProperValues() {
    String alpha = ImageNameLoader.getImageName("svc-alpha", "default/alpha:latest");
    String beta = ImageNameLoader.getImageName("svc-beta", "default/beta:latest");
    String gamma = ImageNameLoader.getImageName("svc-gamma", "default/gamma:latest");

    assertEquals("custom/alpha:b", alpha);
    assertEquals("custom/beta:b", beta);
    assertEquals("custom/gamma:c", gamma);
  }

  @Test
  void givenCachedValue_whenGetImageNameAgain_thenReturnsCachedValue() {
    String first = ImageNameLoader.getImageName("svc-alpha", "default/alpha:latest");
    String second = ImageNameLoader.getImageName("svc-alpha", "default/alpha:other");
    assertNotNull(first);
    assertEquals(first, second);
  }
}
