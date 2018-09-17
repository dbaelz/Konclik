import de.dbaelz.konclik.Parameter
import de.dbaelz.konclik.konclikApp

fun main(args: Array<String>) {
    println("Konclik: Kotlin/Native Command Line Interface Kit")
    println()

    val konclikApp = konclikApp {
        metadata {
            name = "Example Application"
            description = "Demo of the available DSL"
            version = "0.4.2"
        }
        command {
            metadata {
                name = "hello"
                description = "A simple example which prints 'Hello \$user!'"
            }
            parameters {
                arguments = listOf(Parameter.Argument("user"))

                options = listOf(
                        Parameter.Option.Switch("--verbose"),
                        Parameter.Option.Switch("--uppercase"),
                        Parameter.Option.Value("--times"),
                        Parameter.Option.Value("--tags", 3, listOf("#kotlin", "#cli", "#dsl"))
                )
            }
            action { command, providedParameters ->
                val user: String = providedParameters.positionalArguments["user"] ?: "world"

                var text = "Hello $user!"

                if (providedParameters.options.containsKey("--uppercase")) {
                    text = text.toUpperCase()
                }

                providedParameters.options["--times"].orEmpty().let {
                    var times = 1
                    if (it.isNotEmpty()) {
                        try {
                            it.firstOrNull()?.toIntOrNull()?.let {
                                if (it > 0) times = it
                            }
                        } catch (exception: NumberFormatException) {
                        }
                    }
                    (1..times).forEach { println(text) }
                }

                var tags = ""
                providedParameters.options["--tags"]?.forEach {
                    tags += "$it "
                }
                if (tags.isNotEmpty()) println(tags)

                if (providedParameters.options.containsKey("--verbose")) {
                    println()
                    println(command)
                    println(providedParameters)
                }
            }
        }
        command {
            metadata {
                name = "develop"
            }
            parameters {
                options = listOf(
                        Parameter.Option.Choice("--language", setOf("Kotlin", "Java"), "Kotlin")
                )
            }
            action { _, parameters ->
                parameters.options["--language"]?.firstOrNull()?.let {
                    when (it) {
                        "Kotlin" -> println("Yeah! Kotlin!")
                        "Java" -> println("Really? Java?")
                    }
                }
            }
        }
        command {
            metadata {
                name = "echo"
            }
            action { command, providedParameters ->
                println(command)
                println(providedParameters)
            }
            onError { command, error ->
                println("Error executing command \"${command.name}\"")
                println(error.toString())
            }
        }
    }

    konclikApp.run(args.toList())
}
