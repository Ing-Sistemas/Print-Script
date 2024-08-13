package org.example

import Token

class UnaryNode(
    private val token: Token,
    private val startIndex: Int,
    private val endIndex: Int,
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
}