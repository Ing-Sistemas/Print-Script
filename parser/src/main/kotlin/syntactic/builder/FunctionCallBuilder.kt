package org.example.parser.syntactic.builder

import ASTNode
import Token
import org.example.token.TokenType.*
import org.example.token.TokenType.IDENTIFIER

class FunctionCallBuilder : ASTBuilderStrategy {
    private val expectedStruct= listOf(
        CALL,
        OPENING_PARENS,
        CLOSING_PARENS,
        OPENING_CURLY_BRACKS,
        CLOSING_CURLY_BRACKS,
    )

    override fun build(tokens: List<Token>): ASTNode {
        TODO("Not yet implemented")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

}
