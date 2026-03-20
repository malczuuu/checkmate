# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog][keepachangelog], and this project adheres to [Semantic Versioning][semver].

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
