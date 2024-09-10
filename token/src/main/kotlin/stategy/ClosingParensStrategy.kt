package stategy

import Position
import Token
import TokenRegex
import org.example.token.TokenType
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class ClosingParensStrategy : TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.closingParens.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.CLOSING_PARENS, match.value, Position(position, LinePlaceHolder.TEMP_POS)), nextPosition)
        } else { null }
    }
}