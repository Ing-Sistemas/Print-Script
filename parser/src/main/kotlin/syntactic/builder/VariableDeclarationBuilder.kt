package org.example.parser.syntactic.builder

import ASTNode
import AssignmentStatement
import Token
import TypeDeclarationExpression
import VariableDeclarationStatement
import org.example.token.TokenType.*

class VariableDeclarationBuilder : ASTBuilderStrategy {
    private val expectedSize = 7
    private val expectedStruct= listOf(
        KEYWORD,
        IDENTIFIER,
        COLON,
        TYPE, // STRING_TYPE or NUMBER_TYPE
        ASSIGNMENT
    )

    override fun build(tokens: List<Token>): VariableDeclarationStatement {
        val declarator = tokens[expectedStruct.indexOf(KEYWORD)].getValue()
        val type = tokens[expectedStruct.indexOf(TYPE)].getValue()
        return VariableDeclarationStatement(
            declarator,
            TypeDeclarationExpression(type),
            AssignationBuilder().build(tokens)
        )
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size)) return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType ||
                (expectedType == TYPE && (token.getType() == STRING_TYPE || token.getType() == NUMBER_TYPE))
        }
    }

    override fun respectsExpectedSize(size: Int): Boolean {
        return size > expectedSize //needs to be > than expected in order to "contain" expected tokens
    }
}
