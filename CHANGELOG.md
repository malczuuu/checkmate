# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog][keepachangelog], and this project adheres to [Semantic Versioning][semver].

## [Unreleased]

### Added

- Add explicit dependency to JSpecify.

## [0.0.5] - 2026-03-23

### Added

- Add `ImageNamePlugin` SPI for customizing container image names.

## [0.0.4] - 2026-03-22

### Changed

- Transition project from Kotlin to Java as a result of experimentation and preference.

## [0.0.3] - 2026-03-21

### Changed

- Add `@JvmField` to `NamingRules.TEST_CLASSES_MUST_BE_PLURAL`.
- Exclude nested classes from the `NamingRules.TEST_CLASSES_MUST_BE_PLURAL` rule.

## [0.0.2] - 2026-03-21

### Changed

- Make dependencies optional.
- Use autoconfiguration and in-code guards for class presence.

## [0.0.1] - 2026-03-20

### Added

- Annotations for test instrumentation:
    - `@ContainerTest` for marking container-based tests,
    - `@TestListener` for automatic registration and injection of Kafka consumer beans.
- ArchUnit rules enforcing naming conventions for test classes.
- Pre-configured Testcontainer interfaces for Kafka and PostgreSQL.
- Spring context extensions for injectable `TestKafkaConsumer` in `@SpringBootTest`-s.

[keepachangelog]: https://keepachangelog.com/en/1.1.0/

[semver]: https://semver.org/spec/v2.0.0.html
