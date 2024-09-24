package org.example.parser.syntactic.builder

import Token
import org.example.parser.syntactic.SyntacticResult

interface ASTBuilderStrategy {

    fun build(tokens: List<Token>): SyntacticResult

    fun isValidStruct(tokens: List<Token>): Boolean

    fun respectsExpectedSize(size: Int, expectedSize: Int): Boolean {
        return size >= expectedSize // needs to be >= than expected in order to "contain" expected tokens
    }
}