package org.example

import Token
import org.example.token.TokenType


data class StatementNode(
    private val statement: ASTNode,
    private val stopAt: TokenType,
    private val startIndex: Int,
    private val endIndex: Int
): ASTNode {
    override fun getToken(): Token {
        return Token(stopAt, "endOfStatement")
    }

    override fun getStart(): Int {
        return startIndex
    }

    override fun getEnd(): Int {
       return endIndex
    }

    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }

    fun getStatement(): ASTNode {
        return statement
    }
}