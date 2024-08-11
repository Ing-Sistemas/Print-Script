package org.example

import Token


class AssignmentNode(
    private val token: Token,
    private val children: List<ASTNode>
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

    fun getChildren(): List<ASTNode> {
        return children
    }
}