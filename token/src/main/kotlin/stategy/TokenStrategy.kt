package org.example.token.stategy

import Token

interface TokenStrategy {
    fun match(input: String, position: Int): TokenMatch?
}

data class TokenMatch(val token: Token, val nextPosition: Int)