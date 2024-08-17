package org.example

class IdentifierNode(
    private val varIdentifier: String, //variable name
    private val start: Int,
    private val end: Int
): ASTNode {

    override fun getValue(): String {
        return varIdentifier
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