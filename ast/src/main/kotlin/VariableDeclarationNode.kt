package org.example

import Token


class VariableDeclarationNode(
    private val token: Token,
    private val startIndex: Int,
    private val endIndex: Int,
    private val children: List<ASTNode>
) : ASTNode {

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

    fun getChildren(): List<ASTNode> {
        return children
    }


}