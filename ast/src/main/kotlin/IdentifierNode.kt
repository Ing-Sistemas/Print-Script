package org.example

class IdentifierNode(
    private val varIdentifier: String, // variable name
    private val startIndex: Int,
    private val endIndex: Int,
) : ASTNode {

    override fun getValue(): String {
        return varIdentifier
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