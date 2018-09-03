@DslMarker
annotation class KonclikDsl

fun command(block: CommandBuilder.() -> Unit): Command = CommandBuilder().apply(block).build()

@KonclikDsl
class CommandBuilder {
    lateinit var name: String
    var description: String? = ""
    private var arguments = mutableListOf<Parameter.Argument>()
    private var options = mutableListOf<Parameter.Option>()

    var action: ((Command) -> Unit)? = null

    fun parameters(block: ParametersBuilder.() -> Unit) {
        val (arguments, options) = ParametersBuilder().apply(block).build()
        this.arguments = arguments
        this.options = options
    }


    fun build(): Command = Command(name, description, arguments, options, action)
}

@KonclikDsl
class ParametersBuilder {
    var arguments = mutableListOf<Parameter.Argument>()
    var options = mutableListOf<Parameter.Option>()

    fun build(): Pair<MutableList<Parameter.Argument>, MutableList<Parameter.Option>> {
        return Pair(arguments, options)
    }
}
