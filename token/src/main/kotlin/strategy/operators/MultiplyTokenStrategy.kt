package org.example.strategy.operators

import org.example.Token
import org.example.TokenRegex
import org.example.TokenType
import org.example.strategy.TokenMatch
import org.example.strategy.TokenStrategy

class MultiplyTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.multiplyOperatorRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.MULTIPLY_OPERATOR, match.value), nextPosition)
        } else { null }
    }
}