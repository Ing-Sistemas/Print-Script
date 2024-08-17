package org.example

data class StatementNode(
    private val statement: ASTNode,
    private val startIndex: Int,
    private val endIndex: Int
): ASTNode {
    //TODO REMOVE ESTA GARCHA.
    override fun getValue(): String {
        return "Statement"
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

    fun getStatement(): ASTNode {
        return statement
    }
}