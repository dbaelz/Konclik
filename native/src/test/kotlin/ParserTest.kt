import kotlin.test.*
import de.dbaelz.konclik.Command
import de.dbaelz.konclik.Parameter
import de.dbaelz.konclik.ParseResult
import de.dbaelz.konclik.parseArgs

open class ParserTest {

    @Test
    fun `Command without Parameters and no args should return empty maps`() {
        val result = parseArgs(Command("test"), emptyList())

        assertTrue(result is ParseResult.Parameters)
        if (result is ParseResult.Parameters) {
            assertEquals(emptyMap<String, String>(), result.positionalArguments)
            assertEquals(emptyMap<String, List<String>>(), result.options)
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
}