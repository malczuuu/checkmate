package io.github.malczuuu.checkmate.container

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.postgresql.PostgreSQLContainer

interface PostgresAwareTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmField
        val postgresContainer: PostgreSQLContainer = PostgreSQLContainer("postgres:18.3-alpine")
    }
}
