package com.printscript.parser.syntactic.builder

import com.printscript.ast.ASTNode
import com.printscript.token.Token
import com.printscript.token.TokenType.*

interface ASTBuilderStrategy {

    fun build(tokens: List<Token>): ASTNode

    fun isValidStruct(tokens: List<Token>): Boolean

    fun respectsExpectedSize(size: Int, expectedSize: Int): Boolean {
        return size >= expectedSize // needs to be >= than expected in order to "contain" expected tokens
    }
}