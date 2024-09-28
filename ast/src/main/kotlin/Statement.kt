package com.printscript.ast

import com.printscript.token.Position
sealed interface Statement : ASTNode

class VariableDeclarationStatement(
    private val declarator: String,
    private val typeDeclarationExpression: TypeDeclarationExpression,
    private val assignmentStatement: AssignmentStatement,
    private val position: Position,
) : Statement {
    fun getDeclarator(): String = declarator
    fun getTypeDeclarationExpression(): TypeDeclarationExpression = typeDeclarationExpression
    fun getAssignmentExpression(): AssignmentStatement = assignmentStatement
    fun getPosition(): Position = position
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class EmptyVarDeclarationStatement(
    private val declarator: String,
    private val identifier: IdentifierExpression,
    private val typeDeclarationExpression: TypeDeclarationExpression,
    private val position: Position,
) : Statement {
    fun getDeclarator(): String = declarator
    fun getTypeDeclarationExpression(): TypeDeclarationExpression = typeDeclarationExpression
    fun getIdentifier(): IdentifierExpression = identifier
    fun getPosition(): Position = position
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class FunctionCallStatement(
    private val functionName: String,
    private val arguments: List<Expression>,
    private val block: List<ASTNode>?,
    private val position: Position,
) : Statement {
    fun getFunctionName(): String = functionName
    fun getArguments(): List<Expression> = arguments
    fun getBody(): List<ASTNode>? = block
    fun getPosition(): Position = position
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class IfStatement(
    private val condition: Expression,
    private val thenBlock: List<ASTNode>,
    private val elseBlock: List<ASTNode>?,
    private val position: Position,
) : Statement {
    fun getCondition(): Expression = condition
    fun getThenStatement(): List<ASTNode> = thenBlock
    fun getElseStatement(): List<ASTNode>? = elseBlock
    fun getPosition(): Position = position
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class AssignmentStatement(
    private val identifier: IdentifierExpression,
    private val equalOperand: String,
    private val value: Expression,
    private val position: Position,
) : Statement {
    fun getIdentifier(): IdentifierExpression = identifier
    fun getValue(): Expression = value
    fun getEqualOperator(): String = equalOperand
    fun getPosition(): Position = position
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}