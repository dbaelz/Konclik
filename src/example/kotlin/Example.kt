fun main(args: Array<String>) {
    println("Konclik: Kotlin/Native Command Line Interface Kit")

    val helloCommand = command {
        name = "hello"
        description = "A simple example which prints 'Hello \$user!'"
        parameters {
            arguments = mutableListOf(Parameter.Argument("Daniel"))

            options = mutableListOf(
                    Parameter.Option("--verbose"),
                    Parameter.Option("--uppercase")
            )
        }

    }

    parseAndPrintHelloCommand(helloCommand)
}

fun parseAndPrintHelloCommand(command: Command) {
    if (command.name == "hello") {
        if (command.options.contains(Parameter.Option("--verbose")))
            println(command.toString())

        val user = if (command.arguments.isNotEmpty()) command.arguments[0].value else "world"

        "Hello $user!".run {
            if (command.options.contains(Parameter.Option("--uppercase"))) {
                println(this.toUpperCase())
            } else {
                println(this)
            }
        }
    }
}
