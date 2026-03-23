# Agent Instructions - Checkmate Project

## Quick orientation

Multi-module test toolkit for Spring Boot projects.

- `:checkmate-archunit` defines shared architecture rules.
- `:checkmate-annotation` exposes marker annotations used by other modules.
- `:checkmate-container` exposes reusable Testcontainers interfaces.
- `:checkmate-spring-kafka` provides extensions for Spring test context with Kafka.

## Build and test workflows

- Standard full build: `./gradlew spotlessApply build`.
- Code formatting: `./gradlew spotlessApply`.
- Unit tests: `./gradlew test`.
- Integration tests (with Testcontainers): `./gradlew test -Pcontainers.enabled`.

## Project-specific conventions to follow

- Test classes with `@Test`/`@ArchTest` must end with `Tests` (enforced by ArchUnit in each module test suite).
- Container-gated tests use `@ContainerTest` tag (`testcontainers`); tag exclusion is automatic when disabled.
- Each package has `package-info.java` with a description and `@NullMarked` annotation for JSpecify integration.
- Test method names should follow the givenThis_whenThis_thenThis convention (e.g. `givenX_whenY_thenZ`) to keep tests
  descriptive and consistent across the project.

## Integration points and dependencies

- Manage dependencies centrally in `libs.versions.toml`.
- Manage containers image versions in `:checkmate-container` module.
