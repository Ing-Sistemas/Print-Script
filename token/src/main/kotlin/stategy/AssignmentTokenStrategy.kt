package org.example.token.stategy

import Token
import org.example.token.TokenType
import TokenRegex

class AssignmentTokenStrategy: TokenStrategy {
    override fun match(input: String, position: Int): TokenMatch? {
        val match = TokenRegex.assignmentRegex.find(input, position)
        return if (match != null && match.range.first == position) {
            val nextPosition = position + match.value.length
            TokenMatch(Token(TokenType.ASSIGNMENT, match.value), nextPosition)
        } else {null}
    }
}