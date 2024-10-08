
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
        assert(analyzer.isEmpty())
    }

    @Test
    fun testTckTest() {
        val input = "let myVariable: number = 5;"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configurationCamelTrue = configLoader.loadConfiguration("src/main/resources/tck1.json")
        val linter = StaticCodeAnalyzer(configurationCamelTrue, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Identifier myVariable does not match the snake case convention in line: 1 column: 4")
    }

    @Test
    fun emptyConfig() {
        val input = "let myVariable: number = 5; let my_variable: number = 10;"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configuration = configLoader.loadConfiguration("src/main/resources/tck2.json")
        val linter = StaticCodeAnalyzer(configuration, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testTck3() {
        val input = "println(\"Hello\" + \"World!\");"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configuration = configLoader.loadConfiguration("src/main/resources/tck3.json")
        val linter = StaticCodeAnalyzer(configuration, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
    }

    @Test
    fun testReadInp() {
        val input = "let input: string = readInput(\"Enter\" + \"something\");"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configuration = configLoader.loadConfiguration("src/main/resources/readInput.json")
        val linter = StaticCodeAnalyzer(configuration, "1.1")
        val listToken = Lexer("1.1").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
    }

    @Test
    fun testReadInpIn0() {
        val input = "let input: string = readInput(\"Enter\" + \"something\");"
        val inputIterator = input.lineSequence().iterator()
        val configLoader = ConfigLoader
        val configuration = configLoader.loadConfiguration("src/main/resources/readInput.json")
        val linter = StaticCodeAnalyzer(configuration, "1.0")
        val listToken = Lexer("1.0").tokenize(inputIterator).asSequence().toList()
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }
}