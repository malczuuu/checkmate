package io.github.malczuuu.checkmate.container

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.postgresql.PostgreSQLContainer

/**
 * An interface that provides a shared, pre-configured [PostgreSQLContainer] singleton for
 * integration-test classes.
 *
 * Implementing this interface in a Spring Boot test class automatically makes [postgresContainer]
 * available as a [ServiceConnection], so Spring auto-configuration wires all DataSource properties
 * from the running container without any extra setup.
 *
 * Usage:
 * ```kotlin
 * @ContainerTest
 * @SpringBootTest
 * class MyPostgresTests : PostgresAwareTest { ... }
 * ```
 */
interface PostgresAwareTest {

  companion object {
    @Container
    @ServiceConnection
    @JvmField
    val postgresContainer: PostgreSQLContainer = PostgreSQLContainer("postgres:18.3-alpine")
  }
}
