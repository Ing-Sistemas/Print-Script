package org.example

import Token
import org.example.token.TokenType

data class StatementNode(
    private val startIndex: Int,
    private val endIndex: Int,
    private val children: List<Token>
): ASTNode {

    private val token = Token(TokenType.STATEMENT, "statement")

    override fun getToken(): Token {
        return token
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
}