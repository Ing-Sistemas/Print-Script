package org.example.token.stategy

import Token
import TokenRegex
import org.example.token.TokenType

class StringTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.literalStringRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.LITERAL_STRING, match.value.substring(1, match.value.length - 1)), nextPosition)
        } else { null }
    }
}