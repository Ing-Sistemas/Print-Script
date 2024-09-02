package stategy

import Token
import TokenRegex
import org.example.token.TokenType
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class CallStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.functionCallRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.CALL, match.value), nextPosition)
        } else { null }
    }
}