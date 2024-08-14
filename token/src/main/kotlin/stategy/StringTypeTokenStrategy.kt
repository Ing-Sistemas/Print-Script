package org.example.token.stategy

import Token
import org.example.token.TokenType
import TokenRegex

class StringTypeTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.stringTypeRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.STRING_TYPE, match.value), nextPosition)
        } else {null}
    }
}