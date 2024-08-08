package org.example.token.stategy

import Token
import org.example.token.TokenType
import org.example.token.pattern.TokenRegex

class KeywordTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.keywordRegex.find(input, position)
        return if ( match != null && match.range.first == position) {
            val nextPosition = match.value.length + position
            TokenMatch(Token(TokenType.KEYWORD, match.value), nextPosition)
        } else {null}
    }
}