package org.example

import Token

data class CallNode(
    private val token: Token,
    private val arguments: List<ASTNode>,
): ASTNode {

    override fun getToken(): Token {
        TODO("Not yet implemented")
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