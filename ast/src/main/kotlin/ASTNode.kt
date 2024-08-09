package org.example
import Token

sealed interface ASTNode {
    fun getToken(): Token
    fun getStart(): Int
    fun getEnd(): Int
    fun <T> accept(visitor: Visitor<T>): T
}

//TODO ExpressionNode, BinaryOpNode, FunctionDeclarationNode
