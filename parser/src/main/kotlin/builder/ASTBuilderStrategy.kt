package org.example.parser.builder

import Token
import org.example.ASTNode

interface ASTBuilderStrategy {
    fun build(token: Token,tokens: List<Token>): ASTNode
}