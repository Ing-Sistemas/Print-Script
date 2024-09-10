import kotlin.Boolean

sealed interface Literal: Expression {
}

class NumberLiteral(
    private val value: Double,
    private val position: Position
) : Literal {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): Double = value
    fun getPosition(): Position = position
}

class StringLiteral(
    private val value: String,
    private val position: Position
): Literal{
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): String = value
    fun getPosition(): Position = position
}
class BooleanLiteral( //named like this to avoid clash with Boolean class
    private val value: Boolean,
    private val position: Position
):Expression{
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getValue(): Boolean = value
    fun getPosition(): Position = position
}