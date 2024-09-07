sealed interface Statement: ASTNode

class VariableDeclarationStatement(
    private val declarator: String,
    private val typeDeclarationExpression: TypeDeclarationExpression,
    private val assignmentStatement: AssignmentStatement,
) : Statement{
    fun getDeclarator(): String = declarator
    fun getTypeDeclarationExpression(): TypeDeclarationExpression = typeDeclarationExpression
    fun getAssignmentExpression(): AssignmentStatement = assignmentStatement
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class EmptyVarDeclarationStatement(
    private val declarator: String,
    private val typeDeclarationExpression: TypeDeclarationExpression,
    private val identifier: IdentifierExpression
) :Statement{
    fun getDeclarator(): String = declarator
    fun getTypeDeclarationExpression(): TypeDeclarationExpression = typeDeclarationExpression
    fun getIdentifier(): IdentifierExpression = identifier
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class FunctionCallStatement(
    private val functionName: String,
    private val arguments: List<Expression>,
): Statement{
    fun getFunctionName(): String = functionName
    fun getArguments(): List<Expression> = arguments
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class AssignmentStatement(
    private val identifier: IdentifierExpression,
    private val value: Expression,
    private val equalOperand: String
): Statement{
    fun getIdentifier(): IdentifierExpression = identifier
    fun getValue(): Expression = value
    fun getEqualOperator(): String  = equalOperand
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}