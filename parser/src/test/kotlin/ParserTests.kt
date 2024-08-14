import org.example.parser.SyntacticAnalyzer
import org.example.token.TokenType.*


class ParserTests {
}

fun main(){
    val tokens = listOf<Token>(
        Token(KEYWORD, "let"),
        Token(IDENTIFIER, "myVal"),
        Token(COLON, ":"),
        Token(STRING_TYPE,"string"),
        Token(ASSIGNMENT, "="),
        Token(LITERAL_STRING,"'hello'"),
        Token(SEMICOLON, ";"),
        Token(CALL, "println"),
        Token(OPENING_PARENS, "("),
        Token(LITERAL_STRING, "'hola'"),
        Token(CLOSING_PARENS, ")"),
        Token(SEMICOLON,";" )
    )
    val ast = SyntacticAnalyzer().buildAST(tokens)
    println(ast)
}