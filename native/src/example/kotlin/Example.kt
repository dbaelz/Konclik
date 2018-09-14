import de.dbaelz.konclik.Parameter
import de.dbaelz.konclik.konclikApp

fun main(args: Array<String>) {
    println("Konclik: Kotlin/Native Command Line Interface Kit")
    println()

    val konclikApp = konclikApp {
        metadata {
            name = "Example Application"
            description = "An example with two commands"
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
                            times = it.firstOrNull()?.toInt() ?: 1
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
                name = "echo"
            }
            action { command, providedParameters ->
                println(command)
                println(providedParameters)
            }
        }
    }

    konclikApp.run(args.toList())
}
