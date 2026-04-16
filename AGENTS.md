# Agent Instructions - Checkmate Project

## Quick orientation

Single-module test toolkit for Spring Boot projects. All source lives under `src/` in the root project.

- `io.github.malczuuu.checkmate.annotation` exposes marker annotations.
- `io.github.malczuuu.checkmate.archunit` defines shared architecture rules.
- `io.github.malczuuu.checkmate.container` exposes reusable Testcontainers interfaces.
- `io.github.malczuuu.checkmate.spi` defines the `ImageNamePlugin` SPI for overriding container image names.
- `io.github.malczuuu.checkmate.spring.kafka` provides extensions for Spring test context with Kafka.

## Build and test workflows

- Standard full build: `./gradlew spotlessApply build`.
- Code formatting: `./gradlew spotlessApply`.
- Unit tests: `./gradlew test`.
- Integration tests (with Testcontainers): `./gradlew test -Pcontainers.enabled`.

## Project-specific conventions to follow

- Test classes with `@Test`/`@ArchTest` must end with `Tests` (enforced by ArchUnit in the test suite).
- Container-gated tests use `@ContainerTest` tag (`testcontainers`); tag exclusion is automatic when disabled.
- Each package has `package-info.java` with a description and `@NullMarked` annotation for JSpecify integration.
- Test method names should follow the givenThis_whenThis_thenThis convention (e.g. `givenX_whenY_thenZ`) to keep tests
  descriptive and consistent across the project.

## Integration points and dependencies

- Manage dependencies centrally in `libs.versions.toml`.
- Container image versions are defined in the `*AwareTest` interfaces in `io.github.malczuuu.checkmate.container`.
