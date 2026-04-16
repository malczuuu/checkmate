package io.github.malczuuu.checkmate.container.spi;

import java.util.Optional;

/**
 * An SPI for plugins that can provide custom image names for services. Implementations of this
 * interface can be discovered and used by the container testing framework to determine which Docker
 * images to use for specific services during testing.
 */
public interface ImageNamePlugin {

  /**
   * Returns the Docker image name to be used for the specified service. The service name is an
   * identifier that represents a particular service or component in the testing environment (e.g.,
   * {@code "kafka"}, {@code "postgres"}). The returned image name should be a valid Docker image
   * reference that can be pulled from a registry or built locally.
   *
   * @param service the name of the service for which to retrieve the Docker image name
   * @return an {@code Optional} containing the Docker image name for the specified service, or
   *     {@code Optional.empty()} image is not supported
   */
  Optional<String> getImageName(String service);

  /**
   * Returns the priority of this plugin. The lower the value, the higher the priority. When
   * multiple plugins provide an image name for the same service, the plugin with the highest
   * priority (lowest value) will be used.
   *
   * @return the priority
   */
  default int getPriority() {
    return 0;
  }
}
