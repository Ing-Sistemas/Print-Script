package com.printscript.token

class Token(private val tokenType: TokenType, private val value: String, private val position: Position) {

    fun getValue(): String {
        return value
    }
    fun getType(): TokenType {
        return tokenType
    }
    fun getPosition(): Position {
        return position
    }
}