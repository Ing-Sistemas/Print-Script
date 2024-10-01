package com.printscript.parser.syntactic

import com.printscript.token.Token
import com.printscript.token.TokenType

class TokenAccumulator(private val tokens: Iterator<Token>) : Iterator<List<Token>> {
    private var currentList = mutableListOf<Token>()

    private fun getTokenList(): List<Token> {
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == TokenType.IF) {
                return handleIfElseBlock(token)
            } else if (token.getType() == TokenType.SEMICOLON) {
                currentList.add(token)
                val result = currentList.toList()
                currentList.clear()
                return result
            } else {
                currentList.add(token)
            }
        }
        val result = currentList.toList()
        currentList.clear()
        return result
    }

    private fun handleIfElseBlock(ifToken: Token): List<Token> {
        val tokenList = mutableListOf<Token>()
        tokenList.add(ifToken)
        var openBraces = 0
        var hasElse = false

        while (tokens.hasNext()) {
            val token = tokens.next()
            tokenList.add(token)
            if (token.getType() == TokenType.OPENING_BRACES) {
                openBraces++
            } else if (token.getType() == TokenType.CLOSING_BRACES) {
                openBraces--
                if (openBraces == 0) {
                    if (tokens.hasNext()) {
                        val nextToken = tokens.next()
                        if (nextToken.getType() == TokenType.ELSE) {
                            tokenList.add(nextToken)
                            hasElse = true
                            while (tokens.hasNext()) {
                                val elseToken = tokens.next()
                                tokenList.add(elseToken)
                                if (elseToken.getType() == TokenType.OPENING_BRACES) {
                                    openBraces++
                                } else if (elseToken.getType() == TokenType.CLOSING_BRACES) {
                                    openBraces--
                                    if (openBraces == 0) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                    break
                }
            }
        }
        if (!hasElse && tokens.hasNext()) {
            tokens.next()
        }
        return tokenList
    }

    override fun hasNext(): Boolean {
        return tokens.hasNext()
    }

    override fun next(): List<Token> {
        return getTokenList()
    }
}