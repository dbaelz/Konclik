data class Command(val name: String,
                   val description: String = "",
                   val arguments: List<Parameter.Argument> = emptyList(),
                   val options: List<Parameter.Option> = emptyList(),
                   val action: ((Command) -> Unit)? = null) {
    fun getArgument(index: Int): String? {
        return arguments.getOrNull(index)?.value
    }

    fun hasOption(value: String): Boolean {
        return options.contains(Parameter.Option(value))
    }

    fun execute() {
        action?.invoke(this)
    }
}


sealed class Parameter {
    abstract val value: String

    data class Argument(override val value: String) : Parameter()
    data class Option(override val value: String) : Parameter()
}
