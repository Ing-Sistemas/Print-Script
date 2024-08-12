package stategy.operators

import Token
import org.example.token.TokenType
import org.example.token.pattern.TokenRegex
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class MultiplyTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.multiplyOperatorRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.MULTIPLY_OPERATOR, match.value), nextPosition)
        } else {null}
    }
}