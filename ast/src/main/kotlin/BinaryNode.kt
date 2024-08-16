package org.example

data class BinaryNode(
    private val value: String,
    private val left: ASTNode,
    private val right: ASTNode,
    private val startIndex: Int,
    private val endIndex: Int
): ASTNode {
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

    fun getLeft(): ASTNode {
        return left
    }
    fun getRight(): ASTNode {
        return right
    }
}