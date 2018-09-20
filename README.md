# Konclik: Kotlin/Native Command Line Interface Kit
Konclik is a library for the development of a CLI application.

##### Why Konclik?
- Provides a simple yet useful Kotlin DSL to define the application
- It's based on Kotlin/Native, so it works on different platforms
  - Native library for fast, platform independent apps on Linux and macOS without a JVM
  - JAR file to use the library on the JVM and with existing Java code

Issues, contributions and suggestions are very welcome.

## Project structure
The project consists of three gradle submodules:
- `native`: The library developed with Kotlin, which builds the project as Kotlin/Native library
- `jvm`: Submodule to create a JAR containing the class files of the library
- `example`: An example, which demonstrates the usage as Kotlin/Native executable


## Artifacts
Konclik supports the usage as native library due Kotlin/Native and as a JAR file for the JVM.
The artifacts of the library (native and jvm) are provided due Bintray jcenter.
See the Setup section for detailed information how to integrate the library into your project.

### Native library
To build the library with Kotlin/Native, just execute the `build` task of Gradle. This task
downloads all dependencies including Kotlin/Native and creates the library (klib) file.
Currently, Konclik supports `linux` and `macos` as target platforms. More targets will
be supported/tested in the future.

### JAR
The `jvm` submodule creates the jar file with the `assemble` task.
For local testing the `publishToMavenLocal` could be used.
This task generates the POM and copies the library to the local maven cache.

## Setup
Both libraries could be used as Gradle dependencies. Add the maven repository
to your repository list and the library (native or jar) as dependency.
The repository is submitted to jcenter, but it's not accepted yet. So in
the future it should be available due the `jcenter()` repository.

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

#### Native library
```gradle
dependencies {
    implementation "de.dbaelz.konclik:native:0.5.0"
}
```


#### JAR
```gradle
dependencies {
    implementation "de.dbaelz.konclik:jvm:0.5.0"
}
```



## Konclik DSL Example
The current DSL is WIP, but base features are stable and provide enough
feature to build effective CLI applications. The DSL consists of:
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
