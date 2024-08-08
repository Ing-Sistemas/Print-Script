package org.example.token.stategy

import Token
import org.example.token.TokenType
import org.example.token.pattern.TokenRegex

class IdentifierTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.identifierRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.IDENTIFIER, match.value), nextPosition)
        } else {null}
    }
}