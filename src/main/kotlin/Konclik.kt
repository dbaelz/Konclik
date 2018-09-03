@DslMarker
annotation class KonclikDsl

fun command(block: CommandBuilder.() -> Unit): Command = CommandBuilder().apply(block).build()

@KonclikDsl
class CommandBuilder {
    private var metadata: Pair<String, String> = Pair("", "")
    private var arguments = listOf<Parameter.Argument>()
    private var options = listOf<Parameter.Option>()
    private var action: ((Command) -> Unit)? = null

    fun action(block: (Command) -> Unit) {
        action = block
    }

    fun metadata(block: MetadataBuilder.() -> Unit) {
        metadata = MetadataBuilder().apply(block).build()
    }

    fun parameters(block: ParametersBuilder.() -> Unit) {
        val (arguments, options) = ParametersBuilder().apply(block).build()
        this.arguments = arguments
        this.options = options
    }


    fun build(): Command = Command(metadata.first, metadata.second, arguments, options, action)
}

@KonclikDsl
class MetadataBuilder {
    var name: String = ""
    var description: String = ""

    fun build(): Pair<String, String> = Pair(name, description)
}

@KonclikDsl
class ParametersBuilder {
    var arguments = listOf<Parameter.Argument>()
    var options = listOf<Parameter.Option>()

    fun build(): Pair<List<Parameter.Argument>, List<Parameter.Option>> {
        return Pair(arguments, options)
    }
}
