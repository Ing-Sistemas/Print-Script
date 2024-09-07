package org.example.parser.syntactic.builder

import ASTNode
import Token
import org.example.token.TokenType.*

class EmptyVarDecBuilder: ASTBuilderStrategy {
    private val expectedSize = 5
    private val expectedStruct= listOf(
        KEYWORD,
        IDENTIFIER,
        COLON,
        TYPE, // STRING_TYPE or NUMBER_TYPE
    )

    override fun build(tokens: List<Token>): ASTNode {
        TODO("Not yet implemented")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }
}