package org.example

import Token

data class CallNode(
    private val token: Token,
    private val arguments: List<ASTNode>,
    private val startIndex: Int,
    private val endIndex: Int
): ASTNode {

    override fun getToken(): Token {
        return token
    }

    override fun getStart(): Int {
        return startIndex
    }

    override fun getEnd(): Int {
        return endIndex
    }

    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }

    fun getArguments(): List<ASTNode> {
        return arguments
    }
}