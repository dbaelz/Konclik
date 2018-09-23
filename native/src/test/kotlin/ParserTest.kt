import de.dbaelz.konclik.Command
import de.dbaelz.konclik.Parameter
import de.dbaelz.konclik.ParseResult
import de.dbaelz.konclik.parseArgs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

open class ParserTest {

    @Test
    fun `Command without Parameters and no args should return empty maps`() {
        val result = parseArgs(Command("test"), emptyList())

        assertTrue(result is ParseResult.Parameters)
        if (result is ParseResult.Parameters) {
            assertEquals(emptyMap(), result.positionalArguments)
            assertEquals(emptyMap(), result.options)
        }
    }

    @Test
    fun `Command with defaults and no args should return defaults`() {
        val command = Command("test",
                options = listOf(Parameter.Option.Value("--value1", 1, listOf("value1Default")),
                        Parameter.Option.Value("--value2", 2, listOf("value2Default1", "value2Default2")),
                        Parameter.Option.Choice("--choice1", setOf("one", "two"), "two")))

        val result = parseArgs(command, emptyList())

        assertTrue(result is ParseResult.Parameters)
        if (result is ParseResult.Parameters) {
            assertEquals(3, result.options.size)
            assertEquals(listOf("value1Default"), result.options["--value1"])
            assertEquals(listOf("value2Default1", "value2Default2"), result.options["--value2"])
            assertEquals(listOf("two"), result.options["--choice1"])
        }
    }

    @Test
    fun `Command with defaults and args should return parsed args`() {
        val command = Command("test",
                arguments = listOf(Parameter.Argument("argument1")),
                options = listOf(Parameter.Option.Switch("--switch1"),
                        Parameter.Option.Value("--value1", 1, listOf("value1Default")),
                        Parameter.Option.Value("--value2", 2, listOf("value2Default1", "value2Default2")),
                        Parameter.Option.Choice("--choice1", setOf("one", "two"), "two")))
        val args = listOf("someargument", "--switch1", "--value1", "value1Something", "--value2", "value2Something", "--choice1", "one")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Parameters)
        if (result is ParseResult.Parameters) {
            assertEquals(1, result.positionalArguments.size)
            assertEquals("someargument", result.positionalArguments["argument1"])

            assertEquals(4, result.options.size)
            assertEquals(emptyList<String>(), result.options["--switch1"])
            assertEquals(listOf("value1Something"), result.options["--value1"])
            assertEquals(listOf("value2Something", "value2Default1"), result.options["--value2"])
            assertEquals(listOf("one"), result.options["--choice1"])
        }
    }


    // Parsing: Error Cases

    @Test
    fun `Option not available`() {
        val command = Command("test",
                options = listOf(Parameter.Option.Value("--value1", 1, listOf("value1Default"))))
        val args = listOf("--value1", "value1Something", "--value2", "value2Something")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.NO_OPTION_AVAILABLE, result.code)
        }
    }

    @Test
    fun `Not enough values for value option`() {
        val command = Command("test",
                options = listOf(Parameter.Option.Value("--value1", 2, emptyList())))
        val args = listOf("--value1", "somevalue")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.NOT_ENOUGH_VALUES_FOR_OPTION, result.code)
        }
    }

    @Test
    fun `No value provided for choice option`() {
        val command = Command("test",
                options = listOf(Parameter.Option.Choice("--value1", setOf("one", "two"))))
        val args = listOf("--value1")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.NOT_ENOUGH_VALUES_FOR_OPTION, result.code)
        }
    }

    @Test
    fun `Incorrect value provided for choice option`() {
        val command = Command("test",
                options = listOf(Parameter.Option.Choice("--value1", setOf("one", "two"))))
        val args = listOf("--value1", "three")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.INCORRECT_CHOICE_VALUE_PROVIDED, result.code)
        }
    }

    @Test
    fun `More positional arguments provided than expected`() {
        val command = Command("test",
                arguments = listOf(Parameter.Argument("argument1")))
        val args = listOf("somearg", "anotherarg")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.MORE_POSITIONAL_ARGUMENTS_THAN_EXPECTED, result.code)
        }
    }

    @Test
    fun `Positional argument after option`() {
        val command = Command("test",
                arguments = listOf(Parameter.Argument("argument1")),
                options = listOf(Parameter.Option.Switch("--switch1"),
                        Parameter.Option.Value("--value1", 1, listOf("value1Default"))))
        val args = listOf("--switch1", "someargument", "--value1", "value1Something")

        val result = parseArgs(command, args)

        assertTrue(result is ParseResult.Error)
        if (result is ParseResult.Error) {
            assertEquals(ParseResult.Error.Code.POSITIONAL_ARGUMENT_AFTER_OPTION, result.code)
        }
    }
}