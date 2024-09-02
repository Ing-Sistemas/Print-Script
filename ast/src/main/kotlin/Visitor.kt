interface Visitor<T> {
    fun visit(expression: Expression): T
    fun visit(statement: Statement): T
    fun visit(literal: Literal): T
}