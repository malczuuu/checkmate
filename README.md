# Checkmate Project

A multi-module toolkit that provides reusable test utilities for Java and Kotlin Spring Boot projects. Each module is
independently consumable and covers a distinct concern:

- shared architecture rules,
- marker annotations,
- Testcontainers helpers,
- and Spring/Kafka test utilities.

> This project is a personal experiment and might become abandoned at any time.

## Table of Contents

- [Getting Started](#getting-started)
- [`checkmate-annotation`](#checkmate-annotation)
- [`checkmate-archunit`](#checkmate-archunit)
- [`checkmate-container`](#checkmate-container)
- [`checkmate-spring-kafka`](#checkmate-spring-kafka)

## Getting Started

Project is not published to a public repository. To access the module, generate a GitHub personal access token with
`read:packages` scope and configure your Gradle project as follows. Browse https://github.com/settings/tokens to create
a new token.

```properties
# ~/.gradle/gradle.properties - global Gradle properties to avoid accidental commit of credentials
#                               to version control
#
gpr.user={your GitHub username}
gpr.token={your GitHub personal access token with read:packages scope}
```

```kotlin
// build.gradle.kts
//
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/malczuuu/checkmate")
        credentials {
            username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.token")?.toString() ?: System.getenv("GPR_TOKEN")
        }
    }
}

// verify CHANGELOG.md for latest version
dependencies {
    testImplementation("io.github.malczuuu:checkmate-annotation:0.0.4")
    testImplementation("io.github.malczuuu:checkmate-archunit:0.0.4")
    testImplementation("io.github.malczuuu:checkmate-container:0.0.4")
    testImplementation("io.github.malczuuu:checkmate-spring-kafka:0.0.4")
}
```

Building requires Java 25. Usage requires Java 17 or later.

```kotlin
@ContainerTest
@SpringBootTest
internal class OrderProcessingTests : KafkaAwareTest {

    @TestListener("orders")
    private lateinit var ordersConsumer: TestKafkaConsumer

    @TestListener("notifications")
    private lateinit var notificationsConsumer: TestKafkaConsumer

    @BeforeEach
    fun beforeEach() {
        ordersConsumer.clear()
        notificationsConsumer.clear()
    }

    @Test
    fun givenOrder_whenProcessed_thenNotificationSent() {
        // publish to "orders" topic ...

        await().atMost(Duration.ofSeconds(10)).untilAsserted {
            assertThat(notificationsConsumer.poll(Duration.ofMillis(500))).isNotEmpty()
        }
    }
}
```

## `checkmate-annotation`

Marker annotations used across the other modules and by consumer projects.

| Annotation       | Target | Purpose                                                                                                                                       |
|------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| `@ContainerTest` | class  | Applies the `testcontainers` JUnit tag so container-backed tests can be included/excluded from a build via `-Pcontainers.enabled`.            |
| `@TestListener`  | field  | Declares a field as a consumer of a messaging destination. The framework wires the appropriate consumer bean and injects it before each test. |

## `checkmate-archunit`

Shared [ArchUnit](https://www.archunit.org/) rules that can be imported into any module's test suite to enforce
project-wide conventions.

## `checkmate-container`

Interfaces that expose pre-configured, shared Testcontainer singletons. Implementing one of these interfaces in a Spring
Boot test class is enough for Spring auto-configuration to pick up the container's connection details via
`@ServiceConnection`.

| Interface           | Container image        |
|---------------------|------------------------|
| `KafkaAwareTest`    | `apache/kafka:4.2.0`   |
| `PostgresAwareTest` | `postgres:18.3-alpine` |

Container-gated tests must be tagged with `@ContainerTest` (from `checkmate-annotation`).

## `checkmate-spring-kafka`

Extensions for the Spring test context that automate the setup of in-process Kafka consumers during integration tests.

- **`TestKafkaConsumer`** - interface for polling and draining a Kafka topic from test code.
- **`@TestListener`** - annotate a `TestKafkaConsumer` field in your test class with the target
  topic name; the framework registers the consumer bean and injects it automatically.

## Maintenance

1. Releases are published to GitHub Packages on tags following the `v{version}` pattern, e.g. `v0.0.1`.
2. Make sure to configure the `GPR_USER` and `GPR_TOKEN` secrets if tokens expire at ([link][repository-secrets]). Use 
   `repo` and `write:packages` scopes.

[repository-secrets]: https://github.com/malczuuu/checkmate/settings/secrets/actions
