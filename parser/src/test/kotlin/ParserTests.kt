import org.example.parser.Parser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ParserTests {
    @Test
    fun `Valid Variable Declaration`() {
        val code = "let a:string = 'hola';"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "varDec"))
    }

    @Test
    fun `Valid Simple BinaryExpression`() {
        val code = "a = 12;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "simpleBinary"))
    }

    @Test
    fun `Valid Complex BinaryExpression`() {
        val code = "a = ( 12 + 2 ) * 2;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "complexBinary"))
    }

    @Test
    fun `Valid If Function Call`() {
        val code = "if(true){println('hola');} else {println('chau');}"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.1").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "if-else"))
    }

    @Test
    fun `Invalid Variable Declaration String`() {
        val code = "let a:string = 12;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Variable Declaration Number`() {
        val code = "let myVar: number = 'hola';"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Syntactic Expression, missing semicolon`() {
        val code = "println(2 + 9)"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Empty Assignation `() {
        val code = "a = ;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Function Call`() {
        val code = "println();"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Structure`() {
        val code = "a asd asdasda adsasdad asdasdasda asdasda;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    // todo add operation tests for + - * /

    @Test
    fun `Crate Operations For Every Operator`() {
        val code = "println(2 + 9 * 2 / 2 - 2);"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    // TODO check boolean and empty var Semantic

    @Test
    fun `Valid Empty Var Declaration`() {
        val code = "let a:string;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "emptyVarDec"))
    }

    @Test
    fun `Valid Boolean Declaration`() {
        val code = "let a:boolean = true;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.1").tokenize(input)
        val ast = Parser().parse(tokens)
        assert(JsonTester().testJson(ast, "booleanDec"))
    }

    @Test
    fun `Valid Unary Expression`() {
        val code = "a = -x;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        val ast = Parser().parse(tokens)
        println(ast.toString())
        assert(JsonTester().testJson(ast, "unaryExpression"))
    }

    @Test
    fun `valid empty variable declaration`() {
        val code = "let a: string;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.0").tokenize(input)
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `test type declaration node`() {
        val code = "let b: boolean; b = true; b = false;"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.1").tokenize(input)
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `test if case scenario`() {
        val code = "let a: string = 'hola'; if (true) { println('chau'); };"
        val input = listOf(code).iterator()
        val tokens = Lexer("1.1").tokenize(input)
        assertDoesNotThrow { Parser().parse(tokens) }
    }
}