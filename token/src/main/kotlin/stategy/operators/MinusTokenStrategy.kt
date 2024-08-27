package stategy.operators

import Token
import TokenRegex
import org.example.token.TokenType
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class MinusTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.minusOperatorRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.MINUS_OPERATOR, match.value), nextPosition)
        } else { null }
    }
}