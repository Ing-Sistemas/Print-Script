package org.example.parser.syntactic.builder

import ASTNode
import Expression
import NumberLiteral
import Token
import org.example.token.TokenType.*

class ExpressionBuilder: ASTBuilderStrategy {

    override fun build(tokens: List<Token>): Expression {
        TODO("Not yet implemented")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.getType()) {
            MINUS_OPERATOR -> true
            DIVIDE_OPERATOR -> true
            PLUS_OPERATOR -> true
            MULTIPLY_OPERATOR -> true
            else -> false
        }
    }
}