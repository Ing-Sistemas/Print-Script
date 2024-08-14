package org.example

import Token

class AssignmentNode(
    private val token: Token,
    private val identifierNode: ASTNode,
    private val valueNode: ASTNode,
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

    fun getIdentifierNode(): ASTNode {
        return identifierNode
    }
    fun getValueNode(): ASTNode {
        return valueNode
    }
}