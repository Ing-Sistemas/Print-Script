package org.example

class VariableDeclarationNode(
    private val value: String, //let o const
    private val typeDeclarationNode: ASTNode, //string o number
    private val assignmentNode: ASTNode, //ver assignment
    private val startIndex: Int,
    private val endIndex: Int,
) : ASTNode {

    override fun getValue(): String {
        return value
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

    fun getTypeDeclaration(): ASTNode {
        return typeDeclarationNode
    }

    fun getAssignment(): ASTNode {
        return assignmentNode
    }
}