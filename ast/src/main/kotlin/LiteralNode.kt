package org.example

data class LiteralNode(
    private val value: String,
    private val type: String,
    private val start: Int,
    private val end: Int,
) : ASTNode {

    override fun getValue(): String {
        return value
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

    fun getType(): String {
        return type
    }
}