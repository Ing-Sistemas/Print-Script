package org.example.parser.syntactic.builder

import AssignmentStatement
import Token
import TypeDeclarationExpression
import VariableDeclarationStatement
import org.example.parser.syntactic.SyntacticFail
import org.example.parser.syntactic.SyntacticResult
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType.*

class VariableDeclarationBuilder : ASTBuilderStrategy {
    private val expectedStruct = listOf(
        KEYWORD,
        IDENTIFIER,
        COLON,
        TYPE, // STRING_TYPE , NUMBER_TYPE or BOOLEAN_TYPE
        ASSIGNMENT,
    )

    override fun build(tokens: List<Token>): SyntacticResult {
        val declarator = tokens[expectedStruct.indexOf(KEYWORD)]
        val type = tokens[expectedStruct.indexOf(TYPE)]
        return when (val assigment = AssignationBuilder().build(filterTokens(tokens))) {
            is SyntacticFail -> SyntacticFail(assigment.message)
            is SyntacticSuccess -> {
                SyntacticSuccess(
                    VariableDeclarationStatement(
                        declarator.getValue(),
                        TypeDeclarationExpression(type.getValue(), type.getPosition()),
                        assigment.astNode as AssignmentStatement, // TODO check
                        declarator.getPosition(),
                    ),
                )
            }
        }
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType ||
                (
                    expectedType == TYPE && (
                        token.getType() == STRING_TYPE ||
                            token.getType() == NUMBER_TYPE ||
                            token.getType() == BOOLEAN_TYPE
                        )
                    )
        }
    }

    private fun filterTokens(tokens: List<Token>): List<Token> {
        val indexesToRemove = setOf(expectedStruct.indexOf(KEYWORD), expectedStruct.indexOf(COLON), expectedStruct.indexOf(TYPE))
        return tokens.filterIndexed { index, _ -> index !in indexesToRemove } // remove keyword, colon and type, not needed in assigment
    }
}