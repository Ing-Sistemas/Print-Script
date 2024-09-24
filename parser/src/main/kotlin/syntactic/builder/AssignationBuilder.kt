package org.example.parser.syntactic.builder

import AssignmentStatement
import Expression
import IdentifierExpression
import Token
import org.example.parser.syntactic.SyntacticFail
import org.example.parser.syntactic.SyntacticResult
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType.*

class AssignationBuilder : ASTBuilderStrategy {
    private val expectedStruct = listOf(
        IDENTIFIER,
        ASSIGNMENT,
    )

    override fun build(tokens: List<Token>): SyntacticResult {
        val identifierToken = tokens[expectedStruct.indexOf(IDENTIFIER)]
        val assignment = tokens[expectedStruct.indexOf(ASSIGNMENT)]
        return when (val expression = ExpressionBuilder().build(tokens.subList(2, tokens.lastIndex))) {
            is SyntacticSuccess -> {
                val statement = AssignmentStatement(
                    IdentifierExpression(identifierToken.getValue(), identifierToken.getPosition()),
                    assignment.getValue(),
                    expression.astNode as Expression,
                    assignment.getPosition(),
                )
                SyntacticSuccess(statement)
            }
            is SyntacticFail -> SyntacticFail(expression.message)
        }
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType
        }
    }
}