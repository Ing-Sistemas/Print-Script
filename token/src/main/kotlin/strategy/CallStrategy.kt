package org.example.strategy

import org.example.Token
import org.example.TokenRegex
import org.example.TokenType

class CallStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.functionCallRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.CALL, match.value), nextPosition)
        } else { null }
    }
}