package de.dbaelz.konclik

fun parseArgs(command: Command, args: List<String>): ProvidedParameters {
    val providedPositionalArguments = mutableMapOf<String, String>()
    val providedOptions = mutableMapOf<String, String>()

    var positionalArgumentCounter = 0
    var positionalArgumentsHandled = false

    // TODO: Enhance error handling and detect cases like
    // - To many positional arguments provided
    // - Option provided but not used
    val argsListIterator = args.listIterator()
    while (argsListIterator.hasNext()) {
        val arg = argsListIterator.next()

        if (arg.startsWith("-")) {
            // It's an option (starting with "-" or "--")

            // Option found: No more position arguments from here on
            positionalArgumentsHandled = true

            val option = command.getOptionByName(arg)
            option?.let {
                when (option.argType) {
                    Parameter.Option.ArgType.SWITCH -> providedOptions.put(option.name, "")
                    Parameter.Option.ArgType.SINGLE_VALUE -> {
                        try {
                            val optionValue = argsListIterator.next()
                            if (!optionValue.startsWith("-")) {
                                // Use provided value
                                providedOptions.put(option.name, optionValue)
                            } else if (option.defaultValue != null) {
                                // Next arg is an option and not a value: No value provided, so use the default
                                providedOptions[option.name] = option.defaultValue
                                // Move the cursor one backwards so the arg from optionValue is evaluated again
                                argsListIterator.previous()
                            } else {
                                // No value provided and no default
                                // TODO: Handle error
                            }
                        } catch (exception: NoSuchElementException) {
                            // This was the last entry in args: No value was provided
                            if (option.defaultValue != null) {
                                // No value provided, so use the default
                                providedOptions.put(option.name, option.defaultValue)
                            } else {
                                // No value provided and no default
                                // TODO: Handle error
                            }
                        }
                    }
                }
            }

        } else if (!positionalArgumentsHandled) {
            // It's a positional argument
            if (positionalArgumentCounter < command.arguments.size) {
                // Add with key = argument.name, value = arg
                providedPositionalArguments[command.arguments[positionalArgumentCounter].name] = arg
                positionalArgumentCounter++
            } else {
                // Error: more positional arguments provided than expected
                // TODO: Handle error
            }
        } else {
            // Error: Positional argument after Option
            // TODO: Handle error
        }
    }
    return ProvidedParameters(providedPositionalArguments, providedOptions)
}