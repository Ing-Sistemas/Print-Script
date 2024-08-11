package org.example

import Token


class VariableDeclarationNode(
    private val token: Token,
    private val startIndex: Int,
    private val endIndex: Int,
    private val typeDeclarationNode: TypeDeclarationNode,
    private val assignmentNode: AssignmentNode
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

    fun getTypeDeclaration(): TypeDeclarationNode {
        return typeDeclarationNode
    }

    fun getAssignment(): AssignmentNode {
        return assignmentNode
    }
}