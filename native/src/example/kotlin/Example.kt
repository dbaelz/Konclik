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
                arguments = listOf(Parameter.Argument(if (args.isNotEmpty()) args[0] else "Daniel"))

                options = listOf(
                        Parameter.Option("--verbose"),
                        Parameter.Option("--uppercase")
                )
            }
            action { it ->
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
                description = "A command without a name"
            }
            action {
                println(it)
            }
        }
    }

    konclikApp.commands.firstOrNull()?.execute()
}
