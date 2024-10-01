import com.printscript.lexer.Lexer
import com.printscript.linter.configurations.ConfigLoader
import com.printscript.linter.linters.StaticCodeAnalyzer
import com.printscript.token.Token
import org.junit.jupiter.api.Test

class LinterTester {

    @Test
    fun testLinterSnakeFalse() {
        val input = listOf("let val_derrama: string = 'hello argh 76'; println(5 + 5);")
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val tokens = Lexer("1.0").tokenize(input.iterator())
        val listToken = mutableListOf<Token>()
        while (tokens.hasNext()) {
            listToken.add(tokens.next())
        }
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testLinterSnakeTrue() {
        val input = listOf("let val_derrama: string = 'hello argh 76'; println(val_derrama);")
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configSnakeTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val tokens = Lexer("1.0").tokenize(input.iterator())
        val listToken = mutableListOf<Token>()
        while (tokens.hasNext()) {
            listToken.add(tokens.next())
        }
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isEmpty())
    }

    @Test
    fun testLinterCamelFalse() {
        val input = listOf("let val_derrama: string = 'hello argh 76'; println(5 + 5);")
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelFalse.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val tokens = Lexer("1.0").tokenize(input.iterator())
        val listToken = mutableListOf<Token>()
        while (tokens.hasNext()) {
            listToken.add(tokens.next())
        }
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Identifier val_derrama does not match the camel case convention in line: 1 column: 4")
    }

    @Test
    fun testLinterCamelTrue() {
        val input = listOf("let val_derrama: string = 'hello argh 76'; println(val_derrama);")
        val configLoader = ConfigLoader
        val configurationSnakeFalse = configLoader.loadConfiguration("src/main/resources/configCamelTrue.json")
        val linter = StaticCodeAnalyzer(configurationSnakeFalse, "1.0")
        val tokens = Lexer("1.0").tokenize(input.iterator())
        val listToken = mutableListOf<Token>()
        while (tokens.hasNext()) {
            listToken.add(tokens.next())
        }
        val analyzer = linter.analyze(listToken)
        assert(analyzer.isNotEmpty())
        assert(analyzer[0] == "Identifier val_derrama does not match the camel case convention in line: 1 column: 4")
    }
}