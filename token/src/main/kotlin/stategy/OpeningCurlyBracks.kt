package stategy

import Position
import Token
import org.example.token.TokenType
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class OpeningCurlyBracks: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.openingCurlyBrackets.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.OPENING_CURLY_BRACKS, match.value, Position(position, LinePlaceHolder.TEMP_POS)), nextPosition)
        } else { null }
    }
}