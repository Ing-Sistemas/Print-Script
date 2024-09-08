
sealed interface Literal: Expression {
}

class NumberLiteral(
    private val value: Number
) : Literal {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): Number = value
}

class StringLiteral(
    private val value: String
): Literal{
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): String = value
}