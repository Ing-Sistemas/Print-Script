import org.example.parser.Parser
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticSuccess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ParserTests {
    @Test
    fun `Valid Variable Declaration`() {
        val code = "let a:string = 'hola';"
        val tokens = Lexer().tokenize(code)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "varDec"))
    }

    @Test
    fun `Valid Simple BinaryExpression`() {
        val code = "a = 12;"
        val tokens = Lexer().tokenize(code)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "simpleBinary"))
    }

    @Test
    fun `Valid Complex BinaryExpression`() {
        val code = "a = ( 12 + 2 ) * 2;"
        val tokens = Lexer().tokenize(code)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "complexBinary"))
    }

    @Test
    fun `Valid If Function Call`() {
        val code = "if(a){println('hola');};"
        val tokens = Lexer().tokenize(code)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "ifCall"))
    }

    @Test
    fun `Invalid Variable Declaration String`() {
        val code = "let a:string = 12;"
        val tokens = Lexer().tokenize(code)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Variable Declaration Number`() {
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

    @Test
    fun `Invalid Empty Assignation `() {
        val code = "a = ;"
        val tokens = Lexer().tokenize(code)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Function Call`() {
        val code = "println();"
        val tokens = Lexer().tokenize(code)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Structure`() {
        val code = "a asd asdasda adsasdad asdasdasda asdasda;"
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

    // TODO check boolean and empty var Semantic

    @Test
    fun `Valid Empty Var Declaration`() {
        val code = "let a:string;"
        val tokens = Lexer().tokenize(code)
        // val ast = Parser().parse(tokens)
        val result = SyntacticAnalyzer().build(tokens)
        val ast = if (result is SyntacticSuccess) {
            result.astNode
        } else { null }
        assert(result is SyntacticSuccess)
        assert(JsonTester().testJson(ast!!, "emptyVarDec"))
    }

    @Test
    fun `Valid Boolean Declaration`() {
        val code = "let a:boolean = true;"
        val tokens = Lexer().tokenize(code)
        // val ast = Parser().parse(tokens)
        val result = SyntacticAnalyzer().build(tokens)
        val ast = if (result is SyntacticSuccess) {
            result.astNode
        } else { null }
        assert(result is SyntacticSuccess)
        assert(JsonTester().testJson(ast!!, "booleanDec"))
    }

    @Test
    fun `Valid Unary Expression`() {
        val code = "a = -x;"
        val tokens = Lexer().tokenize(code)
        // val ast = Parser().parse(tokens)
        val result = SyntacticAnalyzer().build(tokens)
        val ast = if (result is SyntacticSuccess) {
            result.astNode
        } else { null }
        assert(result is SyntacticSuccess)
        assert(JsonTester().testJson(ast!!, "unaryExpression"))
    }
}