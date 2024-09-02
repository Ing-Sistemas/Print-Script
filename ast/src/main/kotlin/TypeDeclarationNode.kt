package org.example

class TypeDeclarationNode(
    private val type: String, // string or number
    private val startIndex: Int,
    private val endIndex: Int,
) :
    ASTNode {
    override fun getValue(): String {
        return type
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