import configurations.ConfigLoader
import linters.StaticCodeAnalyzer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LinterTester {

    @Test
    fun testLinterSnakeFalse() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val listToken = Lexer("1.0").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testLinterSnakeTrue() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val listToken = Lexer("1.0").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        println(analyzer.toString())
        assert(analyzer[0] == "Cannot use println with val_derrama in line: 1 column: 51")
    }

    @Test
    fun testLinterCamelFalse() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val listToken = Lexer("1.0").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Identifier val_derrama does not match the camelCase convention in line: 1 column: 4")
    }

    @Test
    fun testLinterCamelTrue() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val listToken = Lexer("1.0").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Identifier val_derrama does not match the camelCase convention in line: 1 column: 4")
        assert(analyzer[2] == "Cannot use println with val_derrama in line: 1 column: 51")
    }
}