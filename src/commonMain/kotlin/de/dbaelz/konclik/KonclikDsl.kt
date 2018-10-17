package de.dbaelz.konclik

@DslMarker
annotation class KonclikDsl

/**
 * Creates the CLI application produced with the DSL.
 * @param block The lambda, which defines the CLI application.
 * @return The application created by the DSL.
 */
fun konclikApp(block: KonclikAppBuilder.() -> Unit): KonclikApp = KonclikAppBuilder().apply(block).build()


/**
 * Creates and afterwards runs the CLI application.
 * It could be used as right hand side of a main function.
 * @param args The input args for the CLI application.
 * @param block The lambda, which defines the CLI application.
 * @return Unit so it could be used with main().
 */
fun konclikApp(args: Array<String>, block: KonclikAppBuilder.() -> Unit): Unit =
        KonclikAppBuilder().apply(block).build().run(args.toList())

/**
 * Builder for the KonclikApp.
 * @see KonclikApp
 */
@KonclikDsl
class KonclikAppBuilder {
    private var metadata: Triple<String, String, String> = Triple("", "", "")
    private var commands = mutableListOf<Command>()

    /**
     * Contains the metadata (name, description, version) of the application.
     * @param block The lambda with the metadata.
     * @see AppMetadataBuilder
     */
    fun metadata(block: AppMetadataBuilder.() -> Unit) {
        metadata = AppMetadataBuilder().apply(block).build()
    }

    /**
     * Contains the available commands of the application.
     * @param block The lambda with the commands.
     * @see CommandBuilder
     */
    fun command(block: CommandBuilder.() -> Unit) {
        commands.add(CommandBuilder().apply(block).build())
    }

    /**
     * Builds the application with the provided information.
     * @return The created KonclikApp.
     */
    fun build(): KonclikApp = KonclikApp(metadata.first, metadata.second, metadata.third, commands)
}

/**
 * Builder for the application metadata.
 * These metadata is printed in the application due the help and version parameters
 * @property name The optional name
 * @property description The optional description
 * @property version The version string
 */
@KonclikDsl
class AppMetadataBuilder {
    var name: String = ""
    var description: String = ""
    var version: String = ""

    fun build(): Triple<String, String, String> = Triple(name, description, version)
}

/**
 * Builder for the command. It includes all required arguments, options and logic which define
 * the input and output of the command.
 */
@KonclikDsl
class CommandBuilder {
    private var metadata: Pair<String, String> = Pair("", "")
    private var arguments = listOf<Parameter.Argument>()
    private var options = listOf<Parameter.Option>()
    private var action: ((Command, ParseResult.Parameters) -> Unit)? = null
    private var onError: ((Command, ParseResult.Error) -> Unit)? = null

    /**
     * The action contains the code to execute when the command is called.
     * @param block A lambda with the command and the parsed/evaluated args as parameters.
     */
    fun action(block: (command: Command, parameters: ParseResult.Parameters) -> Unit) {
        action = block
    }

    /**
     * The provided lambda is called when an error occurred due the parsing of the provided args and the
     * evaluation of the command.
     * @param block A lambda with the command and the error model as parameters.
     */
    fun onError(block: (command: Command, error: ParseResult.Error) -> Unit) {
        onError = block
    }

    /**
     * The metadata (name, description) of the command.
     */
    fun metadata(block: MetadataBuilder.() -> Unit) {
        metadata = MetadataBuilder().apply(block).build()
    }

    /**
     * The parameters lambda contains of two list properties: One for the positional arguments
     * and another for the options.
     * @See ParametersBuilder
     */
    fun parameters(block: ParametersBuilder.() -> Unit) {
        val (arguments, options) = ParametersBuilder().apply(block).build()
        this.arguments = arguments
        this.options = options
    }


    fun build(): Command = Command(metadata.first, metadata.second, arguments, options, action, onError)
}

/**
 * The metadata of the command, containing the mandatory name and the optional description.
 * @property name The mandatory name, which identifies a command.
 * @property description The optional description.
 */
@KonclikDsl
class MetadataBuilder {
    lateinit var name: String
    var description: String = ""

    fun build(): Pair<String, String> = Pair(name, description)
}

/**
 * @property arguments The optional list of positional arguments.
 * @property options The optional list of options.
 */
@KonclikDsl
class ParametersBuilder {
    var arguments = listOf<Parameter.Argument>()
    var options = listOf<Parameter.Option>()

    fun build(): Pair<List<Parameter.Argument>, List<Parameter.Option>> {
        return Pair(arguments, options)
    }
}
