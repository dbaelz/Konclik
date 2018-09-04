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
                        Parameter.Option("--verbose"),
                        Parameter.Option("--uppercase")
                )
            }
            action { command, args ->
                val user = command.getArgument("user", args) ?: "world"

                "Hello $user!".run {
                    if (command.hasOption("--uppercase", args)) {
                        println(this.toUpperCase())
                    } else {
                        println(this)
                    }
                }

                if (command.hasOption("--verbose", args)) {
                    println()
                    println("Executed Command: $command")
                }
            }
        }
        command {
            metadata {
                name = "echo"
            }
            action { command, args ->
                println(command)
                println(args)
            }
        }
    }

    // TODO: Just for testing purpose. Should be handled by a args parser
    val argsList = args.toList()
    argsList.firstOrNull()?.let {
        // TODO: See above. Furthermore, changing the list should be part of an internal evaluation of KonclikApp
        konclikApp.findCommand(it)?.execute(argsList.drop(1))
    }
}
