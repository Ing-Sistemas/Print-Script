package stategy

import Token
import org.example.token.TokenType
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class ClosingCurlyBracks: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.closingCurlyBrackets.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.CLOSING_CURLY_BRACKS, match.value), nextPosition)
        } else { null }
    }
}