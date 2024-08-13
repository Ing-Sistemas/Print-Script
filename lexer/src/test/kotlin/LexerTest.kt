import org.example.token.TokenType
import org.junit.jupiter.api.Test

class LexerTest {

    val lexer = Lexer()

    @Test
    fun LexerTokenizes(){
        val input = "let x = 5;"
        val tokens = lexer.tokenize(input)
        assert(tokens.isNotEmpty())
    }

    @Test
    fun firstElement() {
        val input = "let"
        val tokens = lexer.tokenize(input)
        assert (tokens[0].getType() == TokenType.KEYWORD)
    }

    @Test
    fun twoElements() {
        val input = "let my_Var"
        val tokens = lexer.tokenize(input)
        assert(tokens[0].getType() == TokenType.KEYWORD)
        assert(tokens[1].getType() == TokenType.IDENTIFIER)
    }

    @Test
    fun noElement() {
        val input = "     "
        val tokens = lexer.tokenize(input)
        assert(tokens.isEmpty())
    }

    @Test
    fun fullElement() {
        val input = "let myVar: string = 'my';"
        val tokens = lexer.tokenize(input)
        assert(tokens[0].getType() == TokenType.KEYWORD)
        assert(tokens[1].getType() == TokenType.IDENTIFIER)
        assert(tokens[2].getType() == TokenType.COLON)
        assert(tokens[3].getType() == TokenType.STRING_TYPE)
        assert(tokens[4].getType() == TokenType.ASSIGNMENT)
        assert(tokens[5].getType() == TokenType.LITERAL_STRING)
        assert(tokens[6].getType() == TokenType.SEMICOLON)
    }

    @Test
    fun moreThanOneLine() {
        val input = "let myVar: string = 'my'; let myVar2"
        val tokens = lexer.tokenize(input)
        assert(tokens[0].getType() == TokenType.KEYWORD)
        assert(tokens[1].getType() == TokenType.IDENTIFIER)
        assert(tokens[2].getType() == TokenType.COLON)
        assert(tokens[3].getType() == TokenType.STRING_TYPE)
        assert(tokens[4].getType() == TokenType.ASSIGNMENT)
        assert(tokens[5].getType() == TokenType.LITERAL_STRING)
        assert(tokens[6].getType() == TokenType.SEMICOLON)
        assert(tokens[7].getType() == TokenType.KEYWORD)
        assert(tokens[8].getType() == TokenType.IDENTIFIER)
    }
}