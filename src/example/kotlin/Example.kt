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
        action = { it ->
            if (it.options.contains(Parameter.Option("--verbose")))
                println(it.toString())

            val user = if (it.arguments.isNotEmpty()) it.arguments[0].value else "world"

            "Hello $user!".run {
                if (it.options.contains(Parameter.Option("--uppercase"))) {
                    println(this.toUpperCase())
                } else {
                    println(this)
                }

            }
        }
    }

    helloCommand.execute()
}
