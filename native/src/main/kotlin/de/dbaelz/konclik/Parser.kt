package de.dbaelz.konclik

fun parseArgs(command: Command, args: List<String>): ProvidedParameters {
    val providedPositionalArguments = mutableMapOf<String, String>()
    val providedOptions = mutableMapOf<String, List<String>>()

    var positionalArgumentsHandled = false
    var argsHandledCounter = 0

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
                when (option) {
                    is Parameter.Option.Switch -> providedOptions.put(option.name, emptyList())
                    is Parameter.Option.Value -> {
                        val requiredArgs = option.numberArgs - option.defaults.size
                        if (enoughArgsProvided(args.listIterator(argsListIterator.nextIndex()), requiredArgs)) {
                            val values = mutableListOf<String>()
                            while (argsListIterator.hasNext()) {
                                val current = argsListIterator.next()
                                if (current.startsWith("-")) {
                                    // Next option detected: Move the cursor backwards so the arg is evaluated again
                                    argsListIterator.previous()
                                    break
                                } else {
                                    values.add(current)
                                }
                            }
                            if (values.size < option.numberArgs) {
                                // There are less args provided then the option requires
                                // Simply fill up with the defaults beginning with the first one
                                values.addAll(option.defaults.subList(0, option.numberArgs - values.size))
                            }
                            providedOptions.put(option.name, values)
                        } else {
                            // Not enough args provided
                            // TODO: Handle error
                        }
                    }
                }
            }

        } else if (!positionalArgumentsHandled) {
            // It's a positional argument
            if (argsHandledCounter < command.arguments.size) {
                // Add with key = argument.name, value = arg
                providedPositionalArguments[command.arguments[argsHandledCounter].name] = arg
                argsHandledCounter++
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

private fun enoughArgsProvided(iterator: ListIterator<String>, requiredArgs: Int): Boolean {
    if (requiredArgs <= 0) return true

    var remaining = requiredArgs
    while (iterator.hasNext()) {
        if (iterator.next().startsWith("-")) {
            // Next option detected. Check if enough args provided
            return remaining == 0
        } else {
            remaining--
        }
    }
    return remaining == 0
}