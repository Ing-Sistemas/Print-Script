
sealed interface Expression : ASTNode {
    // fun evaluate(): Any?
}

class BinaryExpression(
    private val left: Expression,
    private val operator: String,
    private val right: Expression,
) : Expression {

    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getLeft(): Expression = left
    fun getRight(): Expression = right
    fun getOperator(): String = operator
}

class UnaryExpression(
    private val operator: String,
    private val right: Expression,
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getOperator(): String = operator
    fun getRight(): Expression = right
}

class IdentifierExpression(
    private val identifier: String, // name of identifier
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getIdentifier(): String = identifier
}

class TypeDeclarationExpression(
    private val type: String, // string or number
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getType(): String = type
}