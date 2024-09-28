package com.printscript.lexer

import com.printscript.token.Token
import com.printscript.token.TokenType

class Lexer(
    private val version: String,
) {
    private val tokenPatterns: Map<TokenType, Regex> by lazy {
        TokenPatternProvider().getPatterns(version)
    }

    fun tokenize(input: Iterator<String>): Iterator<Token> {
        return TokenIterator(input, tokenPatterns)
    }
}