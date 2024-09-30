
import com.printscript.lexer.Lexer
import com.printscript.linter.configurations.ConfigLoader
import com.printscript.linter.linters.StaticCodeAnalyzer
import org.junit.jupiter.api.Test

class Linter1Tester {
    @Test
    fun testLinterSnakeFalse() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama); readInput();"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testLinterSnakeTrue() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama); readInput(5);"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testReadInputError() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama); readInput();"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Cannot use readInput with ) in line: 1 column: 75")
    }
}