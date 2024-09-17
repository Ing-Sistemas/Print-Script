package org.example.parser.syntactic

import Token
import org.example.token.TokenType

class TokenAccumulator {
    fun getTokens(tokens: Iterator<Token>): List<Token> {
        val tokenList = mutableListOf<Token>()
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == TokenType.IF) {
                return handleIfBlock(token, tokens)
            }
            if (token.getType() == TokenType.SEMICOLON) {
                tokenList.add(token)
                return tokenList
            }
            tokenList.add(token)
        }
        throw Exception("tokens is empty at ${tokenList.last().getPosition().getColumn()}")
    }

    private fun handleIfBlock(ifStatement: Token, tokens: Iterator<Token>): List<Token> {
        val tokenList = mutableListOf<Token>()
        tokenList.add(ifStatement)
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == TokenType.CLOSING_CURLY_BRACKS && !tokens.hasNext()) {
                tokenList.add(token)
                return tokenList
            }
            tokenList.add(token)
        }
        return tokenList
    }
}