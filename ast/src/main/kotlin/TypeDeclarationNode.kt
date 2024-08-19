package org.example

class TypeDeclarationNode(
    private val type: String, // string or number
    private val start: Int,
    private val end: Int,
) :
    ASTNode {
    override fun getValue(): String {
        return type
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
