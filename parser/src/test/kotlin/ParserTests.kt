import org.example.parser.Parser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ParserTests {

    @Test
    fun `Successful Variable Declaration Analysis`() {
        val code = "let myVar: string = 'hola';"
        val tokens = Lexer().tokenize(code)
        assertDoesNotThrow { Parser().parse(tokens) }

    }

    @Test
    fun `Successful Function Call Analysis`() {
        val code = "println('hola');"
        val tokens = Lexer().tokenize(code)
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Fail Variable Declaration Analysis`() {
        val jsonTester = JsonTester()
        val code = "let myVar: string = 'hola';"
        val tokens = Lexer().tokenize(code)
        val resultAst = Parser().parse(tokens)
        assert(!jsonTester.testJson(resultAst, "functionCall.json"))
    }

    @Test
    fun `Fail Function Call Analysis`() {
        val jsonTester = JsonTester()
        val code = "println('hola');"
        val tokens = Lexer().tokenize(code)
        val resultAst = Parser().parse(tokens)
        assert(!jsonTester.testJson(resultAst, "variableDeclaration.json"))
    }

    @Test
    fun `Successful Binary Expression Analysis`() {
        val code = "println(2 + 9);"
        val tokens = Lexer().tokenize(code)
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Variable Declaration Analysis`() {
        val code = "let myVar: number = 'hola';"
        val tokens = Lexer().tokenize(code)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Syntactic Expression, missing semicolon`() {
        val code = "println(2 + 9)"
        val tokens = Lexer().tokenize(code)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    // todo add operation tests for + - * /

    @Test
    fun `Crate Operations For Every Operator`() {
        val code = "println(2 + 9 * 2 / 2 - 2);"
        val tokens = Lexer().tokenize(code)
        assertDoesNotThrow { Parser().parse(tokens) }
    }
}