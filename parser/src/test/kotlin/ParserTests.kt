import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.token.TokenType.*

class ParserTests

fun main() {
    val tokens = listOf<Token>(
        Token(KEYWORD, "let"),
        Token(IDENTIFIER, "c"),
        Token(COLON, ":"),
        Token(NUMBER_TYPE, "number"),
        Token(ASSIGNMENT, "="),
        Token(IDENTIFIER, "a"),
        Token(DIVIDE_OPERATOR, "/"),
        Token(IDENTIFIER, "b"),
//        Token(LITERAL_STRING,"'hello'"),
//        Token(SEMICOLON, ";"),
//        Token(CALL, "println"),
//        Token(OPENING_PARENS, "("),
//        Token(LITERAL_STRING, "'hola'"),

//        Token(SEMICOLON,";" )
//        Token(IDENTIFIER, "a"),
//        Token(ASSIGNMENT, "="),
//        Token(LITERAL_NUMBER, "8"),
//        Token(PLUS_OPERATOR, "+"),
//        Token(LITERAL_NUMBER, "9"),
//        Token(MINUS_OPERATOR, "-"),
//        Token(LITERAL_NUMBER,"2"),
//        Token(CLOSING_PARENS, ")"),
        Token(SEMICOLON, ";"),
    )
    val ast = SyntacticAnalyzer().buildAST(tokens)
    println(ast)
}