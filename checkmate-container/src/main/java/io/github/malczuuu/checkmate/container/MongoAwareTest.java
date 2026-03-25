package io.github.malczuuu.checkmate.container;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mongodb.MongoDBContainer;

/**
 * Interface to be implemented by tests that require a MongoDB container. It provides a
 * pre-configured MongoDB container with necessary environment variables set for testing purposes.
 */
public interface MongoAwareTest {

  /** The default version of MongoDB to be used in the container. */
  String MONGO_VERSION = "mongo:8.2-alpine";

  /**
   * <strong>Must not be accessed directly by test code.</strong> To be used by test framework only.
   */
  @Container
  @ServiceConnection
  @SuppressWarnings("resource")
  MongoDBContainer mongoContainer =
      new MongoDBContainer(ImageNameLoader.getImageName("mongo", MONGO_VERSION));
}
