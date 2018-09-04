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
            action { it, args ->
                val user = it.getArgument(0) ?: "world"

                "Hello $user!".run {
                    if (it.hasOption("--uppercase")) {
                        println(this.toUpperCase())
                    } else {
                        println(this)
                    }
                }

                if (it.hasOption("--verbose")) {
                    println()
                    println("Executed Command: $it")
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

    val argsList = args.toList()
    argsList.firstOrNull()?.let {
        konclikApp.findCommand(it)?.execute(argsList.drop(1))
    }
}
