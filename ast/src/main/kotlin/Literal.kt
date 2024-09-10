import kotlin.Boolean

sealed interface Literal: Expression {
}

class NumberLiteral(
    private val value: Double
) : Literal {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): Double = value
}

class StringLiteral(
    private val value: String
): Literal{
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): String = value
}
class BooleanLiteral( //named like this to avoid clash with Boolean class
    private val value: Boolean
):Expression{
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): Boolean = value
}