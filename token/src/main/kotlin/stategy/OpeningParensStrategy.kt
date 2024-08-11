package stategy

import Token
import org.example.token.TokenType
import org.example.token.pattern.TokenRegex
import org.example.token.stategy.TokenMatch
import org.example.token.stategy.TokenStrategy

class OpeningParensStrategy : TokenStrategy{
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.opening_parens.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.OPENING_PARENS, match.value), nextPosition)
        } else {null}
    }

}