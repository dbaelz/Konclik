## [Unreleased]
### Added
- Use Travis CI to build and upload the artifacts ([#1](https://github.com/dbaelz/Konclik/pull/1))
- Print help and the current version with the `--help` and `--version` parameter ([#2](https://github.com/dbaelz/Konclik/pull/2))
- Define a CLI application in a `main()` function ([#4](https://github.com/dbaelz/Konclik/pull/4))
- KDoc documentation for the DSL Builders ([#9](https://github.com/dbaelz/Konclik/pull/9))

### Changed
- Utilization of the new multiplatform Gradle plugin ([#3](https://github.com/dbaelz/Konclik/pull/3))


## [0.5.0] - 2018-09-23
### Added
- DSL providing models and builders to create a CLI application
- Parser for the arguments handed over to the app
- Kotlin/Native multiplatform support for `linux` and `macos` targets
- Gradle module to create a JAR targeting the JVM
- Example CLI app to demonstrate the usage