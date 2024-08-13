package stategy

import Token
import org.example.token.TokenType
import TokenRegex
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class ClosingParensStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.closing_parens.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.CLOSING_PARENS, match.value), nextPosition)
        } else {null}
    }
}