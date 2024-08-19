package org.example

data class ProgramNode(
    private val startIndex: Int,
    private val endIndex: Int,
    private val children: List<ASTNode>,
) : ASTNode {

    // Root
    override fun getValue(): String {
        return "Program"
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

    fun getChildren(): List<ASTNode> {
        return children
    }
}
