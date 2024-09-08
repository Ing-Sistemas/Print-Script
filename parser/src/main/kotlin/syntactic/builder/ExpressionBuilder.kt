package org.example.parser.syntactic.builder

import ASTNode
import Expression
import NumberLiteral
import Token

class ExpressionBuilder: ASTBuilderStrategy {

    override fun build(tokens: List<Token>): Expression {
        tokens.forEach { println(it.getType()) }
        return NumberLiteral(2.1)
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }
}