
import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertNotNull

class ParserTests {
    @Nested
    inner class ValidCases {
        @Test
        fun `Valid Variable Declaration`() {
            val version = "1.0"
            val code = "let a:string = 'hola';"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            assert(JsonTester().testJson(ast, "var-dec", version))
        }

        @Test
        fun `Valid Simple BinaryExpression`() {
            val version = "1.0"
            val code = "a = 12;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            assert(JsonTester().testJson(ast, "simple-binary", version))
        }

        @Test
        fun `Valid Complex BinaryExpression`() {
            val version = "1.0"
            val code = "a = ( 12 + 2 ) * 2;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            assert(JsonTester().testJson(ast, "complex-binary", version))
        }

        @Test
        fun `Crate Operations For Every Operator`() {
            val code = "println(2 + 9 * 2 / 2 - 2);"
            val input = listOf(code).iterator()
            val tokens = Lexer("1.0").tokenize(input)
            assertDoesNotThrow { Parser().parse(tokens) }
        }

        @Test
        fun `Valid Empty Var Declaration`() {
            val version = "1.0"
            val code = "let a:string;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            assert(JsonTester().testJson(ast, "empty-var-dec", version))
        }

        @Test
        fun `Valid Boolean Declaration`() {
            val version = "1.1"
            val code = "let a:boolean = true;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            assert(JsonTester().testJson(ast, "boolean-dec", version))
        }

        @Test
        fun `Valid Unary Expression`() {
            val version = "1.0"
            val code = "a = -x;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            val ast = Parser().parse(tokens)
            println(ast.toString())
            assert(JsonTester().testJson(ast, "unary-expression", version))
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
        fun `read env test`() {
            val code = "const name: string = readEnv(\"BEST_FOOTBALL_CLUB\");"
            val input = listOf(code).iterator()
            val tokens = Lexer("1.1").tokenize(input)
            assertDoesNotThrow { Parser().parse(tokens) }
        }

        @Test
        fun `read input test`() {
            val code = "readInput();"
            val input = listOf(code).iterator()
            val tokens = Lexer("1.1").tokenize(input)
            assertDoesNotThrow { Parser().parse(tokens) }
        }

        @Test
        fun `use iterator`() {
            val code = "let a: string = 'hola';"
            val input = listOf(code).iterator()
            val tokens = Lexer("1.1").tokenize(input)
            val astIterator = ASTIterator(tokens, Parser())
            while (astIterator.hasNext()) {
                val ast = astIterator.next()
                assertNotNull(ast)
            }
            assertThrows<Exception> { astIterator.next() }
        }

        @Test
        fun `Valid if function call`() {
            val version = "1.1"
            val file = File("../parser/src/test/resources/testFiles/if-main.ps")
            val input = TestReadIterator().getLineIterator(file.inputStream())
            val tokens = Lexer(version).tokenize(input)
            val astIterator = ASTIterator(tokens, Parser())
            assert(JsonTester().testJsonWithIterator(astIterator, "if-call", version))
        }

        @Test
        fun `Valid if else function call`() {
            val version = "1.1"
            val codeFile = File("../parser/src/test/resources/testFiles/if-else-main.ps")
            val lines = TestReadIterator().getLineIterator(codeFile.inputStream())
            val tokens = Lexer(version).tokenize(lines)
            val astIterator = ASTIterator(tokens, Parser())
            assert(JsonTester().testJsonWithIterator(astIterator, "if-else", version))
        }

        @Test
        fun `Valid if continue function call`() {
            val version = "1.1"
            val codeFile = File("../parser/src/test/resources/testFiles/if-continue-main.ps")
            val lines = TestReadIterator().getLineIterator(codeFile.inputStream())
            val tokens = Lexer(version).tokenize(lines)
            val astIterator = ASTIterator(tokens, Parser())
            assert(JsonTester().testJsonWithIterator(astIterator, "if-continue", version))
        }
    }

    @Nested
    inner class InvalidCases {
        @Test
        fun `Invalid Variable Declaration String`() {
            val version = "1.0"
            val code = "let a:string = 12;"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
            assertThrows<Exception> { Parser().parse(tokens) }
        }

        @Test
        fun `Invalid Variable Declaration Number`() {
            val version = "1.0"
            val code = "let myVar: number = 'hola';"
            val input = listOf(code).iterator()
            val tokens = Lexer(version).tokenize(input)
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
    }
}