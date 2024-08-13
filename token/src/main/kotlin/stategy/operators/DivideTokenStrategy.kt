package stategy.operators

import Token
import org.example.token.TokenType
import TokenRegex
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class DivideTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.divideOperatorRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.DIVIDE_OPERATOR, match.value), nextPosition)
        } else {null}
    }
}