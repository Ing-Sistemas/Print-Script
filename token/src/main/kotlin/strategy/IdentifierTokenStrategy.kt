package org.example.strategy

import org.example.Token
import org.example.TokenRegex
import org.example.TokenType

class IdentifierTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.identifierRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.IDENTIFIER, match.value), nextPosition)
        } else { null }
    }
}