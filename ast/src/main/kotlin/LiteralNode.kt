package org.example

data class LiteralNode(
    private val value: String,
    private val type: String,
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

    fun getType(): String {
        return type
    }
}