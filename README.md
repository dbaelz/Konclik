# Konclik: Kotlin/Native Command Line Interface Kit
[![Build Status](https://travis-ci.com/dbaelz/Konclik.svg?branch=master)](https://travis-ci.com/dbaelz/Konclik)

Konclik is a library for the development of a CLI application.

##### Why Konclik?

- Provides a simple yet useful Kotlin DSL to define the application
- Built with Kotlin's [Multi-platform Project](https://kotlinlang.org/docs/reference/multiplatform.html) tools so you can write once and run everywhere
- Targets Linux, Windows, MacOS, Jvm, and NodeJS

Issues, contributions and suggestions are very welcome.

## Project structure

This project uses the [new MPP plugin](https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html), the root contains Konclik's core source in the `src` directory.
In addition to the core, the following submodules are available.
- `example`: An example, which demonstrates a Konclik app targeting MacOS, Linux, Windows, Jvm, and NodeJS.

## Project Operation

*Note: If the Host machine does not support a specific target, that target's tasks will simply be ignored.*

The following gradle tasks are available:
- Testing: `jsTest`, `macosTest`, `jvmTest`, `linuxTest`, `windowsTest`
- `publish`: Publish to bintray, replace the publishing url with your own repository.
- `publishToMavenLocal`: Publish a version to your local machine, available in the `mavenLocal()` repository
- Example App: `example:runMacos`, `example:runJar`, `example:runLinux`, `example:runWindows`, `example:runNodejs`

## Setup
Konclik is published to bintray.

#### Repository

```gradle
repositories {
    maven {
        url "https://dl.bintray.com/dbaelz/konclik"
    }
    // Other repos like:
    //jcenter()
}
```

#### Download

```gradle
dependencies {
    // With Gradle Metadata enabled, all targets can depend on
    implementation "de.dbaelz.konclik:konclik:0.5.0"
    
    // All artifacts are available with the -target suffix
    implementation "de.dbaelz.konclik:konclik-macos:0.5.0"
    implementation "de.dbaelz.konclik:konclik-linux:0.5.0"
    implementation "de.dbaelz.konclik:konclik-windows:0.5.0"
    implementation "de.dbaelz.konclik:konclik-jvm:0.5.0"
    implementation "de.dbaelz.konclik:konclik-js:0.5.0"
}
```

## Konclik DSL Example
The current DSL is WIP, but base features are stable and provide enough
feature to build effective CLI applications. The DSL consists of:
- `konclikApp`: The CLI app
  * The app provides optional `metadata` (name, description and version)
  * A app consists of one or more `command` entries
  * Use `run()` to parse the provided CLI args and execute the command with these args
  * The app provides a help output showing the available commands, when a invalid command was entered
  * It prints a version info with `--version`
- `command`:
  * It's identified by its `name`. The `description` is optional
  * `parameters` can be defined for the command. They evaluated in the following order:
    * `arguments`: Positional arguments, internally evaluated by the order of the list
    * `options`: Options are optional and could be switch or value parameters
  * The `action` consists of the logic to execute for the command
  * By default, parser errors are printed to standard out. With `onError` this can be changed and custom error handling is possible
  * The command prints a help page with `--help` and returns

For a working example see the CLI application in the example submodule: [Example.kt](https://github.com/dbaelz/Konclik/blob/master/example/src/main/kotlin/Example.kt)


## License
[Apache 2 License](https://github.com/dbaelz/OnOff-Tracker/blob/master/LICENSE)
