package org.example.token.stategy

import Token
import TokenRegex
import org.example.token.TokenType

class SemicolonTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.semicolonRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.SEMICOLON, match.value), nextPosition)
        } else { null }
    }
}