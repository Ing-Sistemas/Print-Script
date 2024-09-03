package org.example.parser.syntactic.builder

import ASTNode
import Token

interface ASTBuilderStrategy {

    fun build(tokens: List<Token>): ASTNode

    fun isValidStruct(tokens: List<Token>): Boolean

}