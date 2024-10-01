package com.printscript.ast

import com.printscript.token.Position

sealed interface Expression : ASTNode {
    // fun evaluate(): Any?
}

class BinaryExpression(
    private val left: Expression,
    private val operator: String,
    private val right: Expression,
    private val position: Position,
) : Expression {

    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getLeft(): Expression = left
    fun getRight(): Expression = right
    fun getOperator(): String = operator
    fun getPosition(): Position = position
}

class UnaryExpression(
    private val operator: String,
    private val right: Expression,
    private val position: Position,
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getOperator(): String = operator
    fun getRight(): Expression = right
    fun getPosition(): Position = position
}

class IdentifierExpression(
    private val identifier: String, // name of identifier
    private val position: Position,
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getIdentifier(): String = identifier
    fun getPosition(): Position = position
}

class TypeDeclarationExpression(
    private val type: String, // string, number or boolean
    private val position: Position,
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getType(): String = type
    fun getPosition(): Position = position
}