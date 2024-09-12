package org.example.strategy

import org.example.Token

interface TokenStrategy {
    fun match(input: String, position: Int): TokenMatch?
}

data class TokenMatch(val token: Token, val nextPosition: Int)