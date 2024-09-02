package org.example.parser.syntactic.builder

import Token

interface ASTBuilderStrategy {
    /**
     * builders look for the index of the given token and start building the AST according to a predefined structure.
     *
     * This structure is defined using the indexes relative to the given token
     */

    fun build(token: Token, tokens: List<Token>): ASTNode
}