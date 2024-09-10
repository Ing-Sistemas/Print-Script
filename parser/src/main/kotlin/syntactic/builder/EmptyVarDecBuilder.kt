package org.example.parser.syntactic.builder

import ASTNode
import EmptyVarDeclarationStatement
import IdentifierExpression
import Token
import TypeDeclarationExpression
import VariableDeclarationStatement
import org.example.token.TokenType.*

class EmptyVarDecBuilder: ASTBuilderStrategy {
    private val expectedStruct= listOf(
        KEYWORD,
        IDENTIFIER,
        COLON,
        TYPE, // STRING_TYPE , NUMBER_TYPE or BOOLEAN_TYPE
    )

    override fun build(tokens: List<Token>): EmptyVarDeclarationStatement {
        val declarator = tokens[expectedStruct.indexOf(KEYWORD)].getValue()
        val type = tokens[expectedStruct.indexOf(TYPE)].getValue()
        val identifier = tokens[expectedStruct.indexOf(IDENTIFIER)].getValue()
        return EmptyVarDeclarationStatement(
            declarator,
            TypeDeclarationExpression(type),
            IdentifierExpression(identifier)
        )
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        if(tokens[expectedStruct.size - 1].getType() == ASSIGNMENT) return false //-1 takes in account the ;
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType ||
                (expectedType == TYPE && (token.getType() == STRING_TYPE ||
                    token.getType() == NUMBER_TYPE ||
                    token.getType() == BOOLEAN_TYPE
                    ))
        }
    }
}