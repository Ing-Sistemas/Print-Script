package org.example

sealed interface ASTNode {
    fun getValue(): String
    fun getStart(): Int
    fun getEnd(): Int
    fun <T> accept(visitor: Visitor<T>): T
}

//TODO ExpressionNode, BinaryOpNode, FunctionDeclarationNode
