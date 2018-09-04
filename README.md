# Konclik: Kotlin/Native Command Line Interface Kit
Konclik is a library for the development of a CLI application defined by
a Kotlin DSL. It's based on Kotlin/Native, so it could work on different platforms.

## Development
Due it's very (very, very) early development stage, the DSL and the
multi-platform features are only partially implemented. Please stay tuned.

Currently, Konclik only supports one target: `linux`. More targets will
be supported/tested in the future. In addition to the Kotlin/Native targets a
JAR file (see jvm submodule) for a simple integration with Kotlin/JVM is planned.

Issues, contributions and Kotlin/Native expertise are very welcome.

## Build
The projects can be build with the integrated Gradle wrapper.
* Execute the `build` task to download all dependencies including Kotlin/Native
and build the project.
* Use the `run` task to execute the main function of the example project,
which includes (soon) examples of the DSL.


## License
[Apache 2 License](https://github.com/dbaelz/OnOff-Tracker/blob/master/LICENSE)
