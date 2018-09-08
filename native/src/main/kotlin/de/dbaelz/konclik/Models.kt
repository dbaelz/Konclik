package de.dbaelz.konclik

data class KonclikApp(val name: String = "",
                      val description: String = "",
                      private val commands: List<Command> = emptyList()) {
    fun findCommand(name: String): Command? = commands.find { it.name == name }
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

    // TODO: Change default value to "T?" and add valueType?
    data class Option(override val name: String,
                      val argType: ArgType = ArgType.SINGLE_VALUE,
                      val defaultValue: String? = null
    ) : Parameter() {
        enum class ArgType {
            SWITCH,
            SINGLE_VALUE
            // TODO: Add more types like MULTI_VALUE. This may requires rethinking of the Option params
        }
    }
}

data class ProvidedParameters(val positionalArguments: Map<String, String> = mapOf(),
                              val options: Map<String, String> = mapOf())
