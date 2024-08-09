package org.example

import Token

data class LeafNode(
    private val token: Token,
): ASTNode {
    override fun getToken(): Token {
        return token
    }

    override fun getStart(): Int {
        TODO("Not yet implemented")
    }

    override fun getEnd(): Int {
        TODO("Not yet implemented")
    }

    override fun <T> accept(visitor: Visitor<T>): T {
        TODO("Not yet implemented")
    }
}