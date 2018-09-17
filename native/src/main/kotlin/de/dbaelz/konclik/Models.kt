package de.dbaelz.konclik

data class KonclikApp(val name: String = "",
                      val description: String = "",
                      val version: String = "",
                      private val commands: List<Command> = emptyList()) {
    fun run(args: List<String> = emptyList()) {
        if (args.isEmpty()) {
            showHelp()
        } else {
            findCommand(args.first())?.execute(args.drop(1))
        }
    }

    private fun showHelp() {
        println("$name: $description")
        if (version.isNotEmpty()) println("Version: $version")
        println()
        println("Available commands:")
        commands.forEach {
            println("${it.name}: ${it.description}")
        }
    }

    private fun findCommand(name: String): Command? = commands.find { it.name == name }
}

data class Command(val name: String,
                   val description: String = "",
                   val arguments: List<Parameter.Argument> = emptyList(),
                   val options: List<Parameter.Option> = emptyList(),
                   val action: ((Command, ParseResult.Parameters) -> Unit)? = null,
                   val onError: ((Command, ParseResult.Error) -> Unit)? = null) {
    fun getOptionByName(name: String): Parameter.Option? {
        return options.find { it.name == name }
    }

    fun execute(args: List<String> = emptyList()) {
        val parseResult = parseArgs(this, args)
        when (parseResult) {
            is ParseResult.Parameters -> action?.invoke(this, parseResult)
            is ParseResult.Error -> if (onError != null) onError.invoke(this, parseResult) else println(parseResult.defaultMessage)
        }
    }
}

sealed class Parameter {
    abstract val name: String

    data class Argument(override val name: String) : Parameter()
    sealed class Option : Parameter() {
        data class Switch(override val name: String) : Option()
        data class Value(override val name: String, val numberArgs: Int = 1, val defaults: List<String> = emptyList()) : Option()
    }
}

sealed class ParseResult {
    data class Parameters(val positionalArguments: Map<String, String> = mapOf(),
                          val options: Map<String, List<String>> = mapOf()) : ParseResult()

    data class Error(val code: Code, val parsedValue: String = "", val defaultMessage: String) : ParseResult() {
        enum class Code {
            NO_OPTION_AVAILABLE,
            NOT_ENOUGH_VALUES_FOR_OPTION,
            MORE_POSITIONAL_ARGUMENTS_THAN_EXPECTED,
            POSITIONAL_ARGUMENT_AFTER_OPTION
        }
    }
}

