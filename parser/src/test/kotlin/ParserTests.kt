import org.example.parser.Parser
import org.example.token.TokenType.*
import org.junit.jupiter.api.Test

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
            Token(LITERAL_STRING, "2"),
            Token(PLUS_OPERATOR, "+"),
            Token(LITERAL_NUMBER, "9"),
            Token(CLOSING_PARENS, ")"),
            Token(SEMICOLON, ";"),
        )
        val resultAst = Parser().parse(tokens)
        assert(jsonTester.testJson(resultAst, "binaryNode.json"))
    }
}