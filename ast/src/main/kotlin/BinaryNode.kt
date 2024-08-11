package org.example

import Token

data class BinaryNode(
    private val token: Token,
    private val left: ASTNode,
    private val right: ASTNode,
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

    fun getLeft(): ASTNode {
        return left
    }
    fun getRight(): ASTNode {
        return right
    }
}