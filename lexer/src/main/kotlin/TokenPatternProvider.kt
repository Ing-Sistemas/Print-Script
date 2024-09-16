import org.example.token.TokenType
import org.example.token.TokenType.*

class TokenPatternProvider {
    fun getPatterns(version: String): Map<TokenType, Regex> {
        val tokenPatterns: Map<TokenType, Regex> = when (version) {
            "1.0" -> mapOf(
                ASSIGNMENT to Regex("="),
                SEMICOLON to Regex(";"),
                COLON to Regex(":"),
                KEYWORD to Regex("\\b(let)\\b"),
                LITERAL_NUMBER to Regex("\\d+(\\.\\d+)?"),
                LITERAL_STRING to Regex("\".*?\"|'.*?'"),
                STRING_TYPE to Regex("string"),
                NUMBER_TYPE to Regex("number"),
                PLUS_OPERATOR to Regex("\\+"),
                MINUS_OPERATOR to Regex("-"),
                MULTIPLY_OPERATOR to Regex("\\*"),
                DIVIDE_OPERATOR to Regex("/"),
                FUNCTION_CALL to Regex("\\b(println)\\b"),
                OPENING_PARENS to Regex("\\("),
                CLOSING_PARENS to Regex("\\)"),
                IDENTIFIER to Regex("[a-zA-Z_][a-zA-Z_0-9]*"),
            )

            "1.1" -> mapOf(
                KEYWORD to Regex("\\b(let|const)\\b"),
                ASSIGNMENT to Regex("="),
                SEMICOLON to Regex(";"),
                COLON to Regex(":"),
                LITERAL_NUMBER to Regex("\\d+(\\.\\d+)?"),
                LITERAL_STRING to Regex("\".*?\"|'.*?'"),
                STRING_TYPE to Regex("string"),
                NUMBER_TYPE to Regex("number"),
                PLUS_OPERATOR to Regex("\\+"),
                MINUS_OPERATOR to Regex("-"),
                MULTIPLY_OPERATOR to Regex("\\*"),
                DIVIDE_OPERATOR to Regex("/"),
                FUNCTION_CALL to Regex("\\b(if|else|println)\\b"),
                OPENING_PARENS to Regex("\\("),
                CLOSING_PARENS to Regex("\\)"),
                OPENING_CURLY_BRACKS to Regex("\\{"),
                CLOSING_CURLY_BRACKS to Regex("}"),
                BOOLEAN_TYPE to Regex("boolean"),
                BOOLEAN to Regex("true|false"),
                IDENTIFIER to Regex("[a-zA-Z_][a-zA-Z_0-9]*"),
            )
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
        return tokenPatterns
    }
}