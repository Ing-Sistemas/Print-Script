package org.example.token.stategy

import Position
import Token
import TokenRegex
import org.example.token.TokenType

class NumberTokenStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.literalNumberRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.LITERAL_NUMBER, match.value, Position(position, LinePlaceHolder.TEMP_POS)), nextPosition)
            // ^ Podria ser un boolean pero lo hice un tipo de dato por si necesitamos mas info en un futuro
            // Pq en en ast explorer dice un from y to de cada token y eso o nos lop pueden llegar a pedir
            // o nos puede servir si necesitamos decirle al usuario donde esta el error
        } else { null }
    }
}