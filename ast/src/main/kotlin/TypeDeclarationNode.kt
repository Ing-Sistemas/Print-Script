package org.example

import Token


class TypeDeclarationNode(
    private val token: Token,
    private val start: Int,
    private val end: Int,
)
    : ASTNode {
    override fun getToken(): Token {
        return token
    }

    override fun getStart(): Int {
        return start
    }

    override fun getEnd(): Int {
        return end
    }

    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}