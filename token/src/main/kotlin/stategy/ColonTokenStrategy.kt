package org.example.token.stategy

import Position
import Token
import TokenRegex
import org.example.token.TokenType

class ColonTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.colonRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.COLON, match.value, Position(position, LinePlaceHolder.TEMP_POS)), nextPosition)
        } else { null }
    }
}