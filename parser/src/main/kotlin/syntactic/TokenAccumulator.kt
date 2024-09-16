package org.example.parser.syntactic

import Token
import org.example.token.TokenType

class TokenAccumulator {
    fun getTokens(tokens: Iterator<Token>): List<Token> {
        val tokenList = mutableListOf<Token>()
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == TokenType.SEMICOLON){
                tokenList.add(token)
                return tokenList
            }
            tokenList.add(token)
        }
        throw Exception("tokens is empty at ${tokenList.last().getPosition().getColumn()}")
    }
}