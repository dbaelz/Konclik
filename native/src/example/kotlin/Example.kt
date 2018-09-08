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
                        Parameter.Option("--verbose", Parameter.Option.ArgType.SWITCH),
                        Parameter.Option("--uppercase", Parameter.Option.ArgType.SWITCH),
                        Parameter.Option("--times", Parameter.Option.ArgType.SINGLE_VALUE, 1.toString())
                )
            }
            action { command, providedParameters ->
                val user: String = providedParameters.positionalArguments["user"] ?: "world"

                "Hello $user!".run {
                    if (providedParameters.options.containsKey("--uppercase")) {
                        println(this.toUpperCase())
                    } else {
                        println(this)
                    }
                }

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

    konclikApp.execute(args.toList())
}
