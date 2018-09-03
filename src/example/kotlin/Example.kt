fun main(args: Array<String>) {
    println("Konclik: Kotlin/Native Command Line Interface Kit")
    println()

    command {
        name = "hello"
        description = "A simple example which prints 'Hello \$user!'"
        parameters {
            arguments = listOf(Parameter.Argument(if (args.isNotEmpty()) args[0] else "Daniel"))

            options = listOf(
                    Parameter.Option("--verbose"),
                    Parameter.Option("--uppercase")
            )
        }
        action = { it ->
            val user = if (it.arguments.isNotEmpty()) it.arguments[0].value else "world"

            "Hello $user!".run {
                if (it.options.contains(Parameter.Option("--uppercase"))) {
                    println(this.toUpperCase())
                } else {
                    println(this)
                }
            }

            if (it.options.contains(Parameter.Option("--verbose"))) {
                println()
                println("Executed Command: $it")
            }
        }
    }.execute()
}
