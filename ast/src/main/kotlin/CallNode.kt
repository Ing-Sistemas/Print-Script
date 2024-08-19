package org.example

data class CallNode(
    private val functionCall: String, // println, if
    private val arguments: List<ASTNode>,
    private val startIndex: Int,
    private val endIndex: Int,
) : ASTNode {

    override fun getValue(): String {
        return functionCall
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

    fun getArguments(): List<ASTNode> {
        return arguments
    }
}
