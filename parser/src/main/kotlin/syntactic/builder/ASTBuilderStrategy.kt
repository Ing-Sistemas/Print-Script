package org.example.parser.syntactic.builder

import ASTNode
import Token

interface ASTBuilderStrategy {

    fun build(tokens: List<Token>): ASTNode

    fun isValidStruct(tokens: List<Token>): Boolean

    fun respectsExpectedSize(size: Int, expectedSize: Int): Boolean {
        return size >= expectedSize // needs to be >= than expected in order to "contain" expected tokens
    }
}