package de.dbaelz.konclik

data class KonclikApp(val name: String = "",
                      val description: String = "",
                      private val commands: List<Command> = emptyList()) {
    private fun findCommand(name: String): Command? = commands.find { it.name == name }

    fun run(args: List<String> = emptyList()) {
        // TODO: Handle empty args including "no command"? Show some kind of help text?
        args.firstOrNull()?.let {
            // Find the command and hand over the args (without the first arg which was the command name)
            findCommand(it)?.execute(args.drop(1))
        }
    }
}

data class Command(val name: String,
                   val description: String = "",
                   val arguments: List<Parameter.Argument> = emptyList(),
                   val options: List<Parameter.Option> = emptyList(),
                   val action: ((Command, ProvidedParameters) -> Unit)? = null) {
    fun getOptionByName(name: String): Parameter.Option? {
        return options.find { it.name == name }
    }

    fun execute(args: List<String> = emptyList()) {
        action?.invoke(this, parseArgs(this, args))
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

data class ProvidedParameters(val positionalArguments: Map<String, String> = mapOf(),
                              val options: Map<String, List<String>> = mapOf())
