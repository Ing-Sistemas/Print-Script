package org.example.token.stategy

import Position
import Token
import TokenRegex
import org.example.token.TokenType

class KeywordTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.keywordRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = match.value.length + position
            TokenMatch(Token(TokenType.KEYWORD, match.value, Position(position, LinePlaceHolder.TEMP_POS)), nextPosition)
        } else { null }
    }
}