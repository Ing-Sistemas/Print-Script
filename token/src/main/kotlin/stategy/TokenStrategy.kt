package org.example.token.stategy

import Token

// interfaz para definir las estrategias (por ahora solo las q pidio tomi)
interface TokenStrategy {
    fun match(input: String, position: Int): TokenMatch?
}

data class TokenMatch(val token: Token, val nextPosition: Int)