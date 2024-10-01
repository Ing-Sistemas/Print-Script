package com.printscript.parser.syntactic.builder

import com.printscript.ast.AssignmentStatement
import com.printscript.ast.Expression
import com.printscript.ast.IdentifierExpression
import com.printscript.parser.syntactic.SyntacticFail
import com.printscript.parser.syntactic.SyntacticResult
import com.printscript.parser.syntactic.SyntacticSuccess
import com.printscript.token.Token
import com.printscript.token.TokenType.*

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