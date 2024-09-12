package org.example.strategy

import org.example.Token
import org.example.TokenRegex
import org.example.TokenType

class KeywordTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.keywordRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = match.value.length + position
            TokenMatch(Token(TokenType.KEYWORD, match.value), nextPosition)
        } else { null }
    }
}