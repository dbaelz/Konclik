# Konclik: Kotlin/Native Command Line Interface Kit
Konclik is a library for the development of a CLI application defined by
a Kotlin DSL. It's based on Kotlin/Native, so it could work on different platforms.

## Development
Due it's early development stage, the DSL only provides some basic features.
Please stay tuned.

Currently, Konclik only supports one target: `linux`. More targets will
be supported/tested in the future. In addition to the Kotlin/Native targets a
JAR file (see jvm submodule) for a simple integration with Kotlin/JVM is planned.

Issues, contributions and Kotlin/Native expertise are very welcome.

## Build the project
The `native` submodule can be build with the integrated Gradle wrapper.
* Execute the `build` task to download all dependencies including Kotlin/Native
and build the project.
* Use the `run` task to execute the main function of the example project without args

## DSL Example/Usage
The DSL is still WIP and might be changed in the future.
At the moment, some basic features are supported. The DSL consists of:
- `konclikApp`: The CLI app
  * The app provides optional `metadata`
  * A app consists of one or more `command` entries
  * Use `execute()` to parse the provided CLI args and execute the command
- `command`:
  * It's identified by its `name`. The `description` is optional
  * `parameters` can be defined for the command. They evaluated in the following order:
    * `arguments`: Positional arguments, internaly evaluated by the order of the list
    * `options`: Options are optional and could be switch or single value parameters
  * The `action` consists of the logic

For a working example see the CLI application in [Example.kt](https://github.com/dbaelz/Konclik/blob/master/native/src/example/kotlin/Example.kt).



## License
[Apache 2 License](https://github.com/dbaelz/OnOff-Tracker/blob/master/LICENSE)
