package org.example.parser.syntactic.builder

import AssignmentStatement
import IdentifierExpression
import Literal
import NumberLiteral
import Token
import UnaryExpression
import org.example.token.TokenType.*

class AssignationBuilder : ASTBuilderStrategy {
    private val expectedStruct= listOf(
        IDENTIFIER,
        ASSIGNMENT
    )

    override fun build(tokens: List<Token>): AssignmentStatement {
        val identifier = IdentifierExpression(tokens[expectedStruct.indexOf(IDENTIFIER)].getValue())
        val assignment = tokens[expectedStruct.indexOf(ASSIGNMENT)].getValue()
        return AssignmentStatement(
            identifier,
            ExpressionBuilder().build(tokens.subList(2, tokens.lastIndex)),
            assignment
        )
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType
        }
    }
}