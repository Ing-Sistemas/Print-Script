import configurations.ConfigLoader
import linters.StaticCodeAnalyzer
import org.junit.jupiter.api.Test

class LinterTester {

    @Test
    fun testLinterSnakeFalse() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse)
        val listToken = Lexer().tokenize(input)
        val analyzer = linter.analyze(listToken)
    }

    @Test
    fun testLinterSnakeTrue() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse)
        val listToken = Lexer().tokenize(input)
        val analyzer = linter.analyze(listToken)
    }

    @Test
    fun testLinterCamelFalse() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse)
        val listToken = Lexer().tokenize(input)
        val analyzer = linter.analyze(listToken)
    }

    @Test
    fun testLinterCamelTrue() {
        val input = "let val_derrama: string = 'hello argh 76'; println(val_derrama);"
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse)
        val listToken = Lexer().tokenize(input)
        val analyzer = linter.analyze(listToken)
    }
}