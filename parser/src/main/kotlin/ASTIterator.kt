package com.printscript.parser

import com.printscript.ast.ASTNode
import com.printscript.token.Token

class ASTIterator(
    private val tokens: Iterator<Token>,
    private val parser: Parser,
) : Iterator<ASTNode> {
    private var errorMessage: String? = null

    override fun hasNext(): Boolean {
        return tokens.hasNext()
    }

    override fun next(): ASTNode {
        if (errorMessage != null) {
            throw Exception(errorMessage)
        }
        if (!hasNext()) {
            throw NoSuchElementException("No more AST nodes")
        }
        return fetchNextNode() ?: throw Exception(errorMessage)
    }

    private fun fetchNextNode(): ASTNode? {
        try {
            val ast = parser.parse(tokens)
            return ast
        } catch (e: Exception) {
            errorMessage = e.message
            return null
        }
    }
}