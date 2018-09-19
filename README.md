# Konclik: Kotlin/Native Command Line Interface Kit
Konclik is a library for the development of a CLI application defined by
a Kotlin DSL. It's based on Kotlin/Native, so it could work on different platforms.
Issues, contributions and Kotlin/Native expertise are very welcome.


## Project structure
The project consists of three gradle submodules:
- `native`: The library developed with Kotlin, which builds the project as Kotlin/Native library
- `jvm`: Submodule to create a JAR containing the class files of the library
- `example`: An example, which demonstrates the usage as Kotlin/Native executable


## Artifacts
Konclik supports the usage as native library due Kotlin/Native and as a JAR file for the JVM.
In the future both artifacts will be provided due Maven Central.

### Native library
To build the library with Kotlin/Native, just execute the `build` task of Gradle. This task
downloads all dependencies including Kotlin/Native and creates the library (klib) file.
Currently, Konclik supports `linux` and `macos` as target platforms. More targets will
be supported/tested in the future.

### JAR
In addition to the native library, Konclik could be used on the JVM. The `jvm` submodule creates
the jar file with the `assemble` task. For local testing the `publishToMavenLocal` could be used.
This task generates the POM and copies the library to the local maven cache.

## Konclik DSL Example Example/Usage
The DSL is WIP, but provides enough features to build a basic CLI application.

The DSL consists of:
- `konclikApp`: The CLI app
  * The app provides optional `metadata` (name, description and version)
  * A app consists of one or more `command` entries
  * Use `run()` to parse the provided CLI args and execute the command with these args
- `command`:
  * It's identified by its `name`. The `description` is optional
  * `parameters` can be defined for the command. They evaluated in the following order:
    * `arguments`: Positional arguments, internally evaluated by the order of the list
    * `options`: Options are optional and could be switch or value parameters
  * The `action` consists of the logic to execute for the command
  * By default, parser errors are printed to standard out.
  With `onError` this can be changed and custom error handling is possible

For a working example see the CLI application in the example submodule: [Example.kt](https://github.com/dbaelz/Konclik/blob/master/example/src/main/kotlin/Example.kt)


## License
[Apache 2 License](https://github.com/dbaelz/OnOff-Tracker/blob/master/LICENSE)
