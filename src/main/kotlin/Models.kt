data class Command(val name: String,
                   val description: String?,
                   val arguments: List<Parameter.Argument> = emptyList(),
                   val options: List<Parameter.Option> = emptyList())


sealed class Parameter {
    abstract val value: String

    data class Argument(override val value: String) : Parameter()
    data class Option(override val value: String) : Parameter()
}
