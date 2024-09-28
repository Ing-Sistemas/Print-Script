package com.printscript.ast

import com.printscript.token.Position

class ReadEnvNode(
    private val identifierName: String,
    private val position: Position,
) : ASTNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
    fun getIdentifierName(): String = identifierName
    fun getPosition(): Position = position
}