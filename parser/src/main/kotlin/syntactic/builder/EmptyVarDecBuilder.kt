package org.example.parser.syntactic.builder

import EmptyVarDeclarationStatement
import IdentifierExpression
import Token
import TypeDeclarationExpression
import org.example.token.TokenType.*

class EmptyVarDecBuilder: ASTBuilderStrategy {
    private val expectedStruct= listOf(
        KEYWORD,
        IDENTIFIER,
        COLON,
        TYPE, // STRING_TYPE , NUMBER_TYPE or BOOLEAN_TYPE
    )

    override fun build(tokens: List<Token>): EmptyVarDeclarationStatement {
        val declarator = tokens[expectedStruct.indexOf(KEYWORD)]
        val type = tokens[expectedStruct.indexOf(TYPE)]
        val identifierToken = tokens[expectedStruct.indexOf(IDENTIFIER)]
        return EmptyVarDeclarationStatement(
            declarator.getValue(),
            IdentifierExpression(identifierToken.getValue(), identifierToken.getPosition()),
            TypeDeclarationExpression(type.getValue(),type.getPosition()),
            declarator.getPosition()
        )
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        if (!respectsExpectedSize(tokens.size, expectedStruct.size)) return false
        if(tokens[expectedStruct.size - 1].getType() == ASSIGNMENT) return false //-1 takes in account the ;
        if(tokens[expectedStruct.indexOf(KEYWORD)].getValue() != "let") return false
        return tokens.zip(expectedStruct).all { (token, expectedType) ->
            token.getType() == expectedType ||
                (expectedType == TYPE && (token.getType() == STRING_TYPE ||
                    token.getType() == NUMBER_TYPE ||
                    token.getType() == BOOLEAN_TYPE
                    ))
        }
    }
}