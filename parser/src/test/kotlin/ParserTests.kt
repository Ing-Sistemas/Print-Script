import org.example.Parser
import org.example.Token
import org.example.TokenType.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ParserTests {

    @Test
    fun `Successful Variable Declaration Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(KEYWORD, "let"),
            Token(IDENTIFIER, "myVar"),
            Token(COLON, ":"),
            Token(STRING_TYPE, "string"),
            Token(ASSIGNMENT, "="),
            Token(LITERAL_STRING, "'hola'"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(jsonTester.testJson(resultAst, "variableDeclaration.json"))
    }

    @Test
    fun `Successful Function Call Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_STRING, "'hola'"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(jsonTester.testJson(resultAst, "functionCall.json"))
    }

    @Test
    fun `Fail Variable Declaration Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(KEYWORD, "let"),
            Token(IDENTIFIER, "myVar"),
            Token(COLON, ":"),
            Token(STRING_TYPE, "string"),
            Token(ASSIGNMENT, "="),
            Token(LITERAL_STRING, "'hola'"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(!jsonTester.testJson(resultAst, "functionCall.json"))
    }

    @Test
    fun `Fail Function Call Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_STRING, "'hola'"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(!jsonTester.testJson(resultAst, "variableDeclaration.json"))
    }

    @Test
    fun `Successful Binary Expression Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "+"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(jsonTester.testJson(resultAst, "binaryNode.json"))
    }

    @Test
    fun `Invalid Variable Declaration Analysis`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(KEYWORD, "let"),
            Token(IDENTIFIER, "myVar"),
            Token(COLON, ":"),
            Token(STRING_TYPE, "number"),
            Token(ASSIGNMENT, "="),
            Token(LITERAL_STRING, "'hola'"),
            Token(SEMICOLON, ";"),
        )
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    @Test
    fun `Invalid Syntactic Expression, missing semicolon`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "+"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
        )
        assertThrows<Exception> { Parser().parse(tokens) }
    }

    // todo add operation tests for + - * /

    @Test
    fun `Crate Operations For Sum Operator`() {
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "+"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Crate Operations For Substractin Operator`() {
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "-"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Crate Operations For Multiply Operator`() {
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "*"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Crate Operations For Divide Operator`() {
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "/"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        assertDoesNotThrow { Parser().parse(tokens) }
    }

    @Test
    fun `Successful Binary Node Visit`() {
        val jsonTester = JsonTester()
        val tokens = listOf<Token>(
            Token(CALL, "println"),
            Token(OPENING_PARENS, "("),
            Token(LITERAL_NUMBER, "2"),
            Token(PLUS_OPERATOR, "+"),
            Token(LITERAL_NUMBER, "3"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        assertDoesNotThrow { Parser().parse(tokens) }
    }
}