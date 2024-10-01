package com.printscript.parser.syntactic.builder

import com.printscript.parser.syntactic.SyntacticResult
import com.printscript.token.Token

interface ASTBuilderStrategy {

    fun build(tokens: List<Token>): SyntacticResult

    fun isValidStruct(tokens: List<Token>): Boolean

    fun respectsExpectedSize(size: Int, expectedSize: Int): Boolean {
        return size >= expectedSize // needs to be >= than expected in order to "contain" expected tokens
    }
}