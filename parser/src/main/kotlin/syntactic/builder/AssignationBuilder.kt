package org.example.parser.syntactic.builder

import AssignmentStatement
import IdentifierExpression
import Token
import org.example.token.TokenType.*

class AssignationBuilder : ASTBuilderStrategy {
    private val expectedStruct= listOf(
        IDENTIFIER,
        ASSIGNMENT
    )

    override fun build(tokens: List<Token>): AssignmentStatement {

        val identifierToken = tokens[expectedStruct.indexOf(IDENTIFIER)]
        val assignment = tokens[expectedStruct.indexOf(ASSIGNMENT)]
        return AssignmentStatement(
            IdentifierExpression(identifierToken.getValue(),identifierToken.getPosition()),
            assignment.getValue(),
            ExpressionBuilder().build(tokens.subList(2, tokens.lastIndex)),
            assignment.getPosition()
        )
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType
        }
    }
}