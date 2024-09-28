package com.printscript.parser

import com.printscript.ast.ASTNode
import com.printscript.token.Token

class ASTIterator(
    private val tokens: Iterator<Token>,
    private val parser: Parser,
) : Iterator<ASTNode> {
    private var nextNode: ASTNode? = null
    private var errorMessage: String? = null

    override fun hasNext(): Boolean {
        if (nextNode == null && tokens.hasNext()) {
            nextNode = fetchNextNode()
        }
        return nextNode != null
    }

    override fun next(): ASTNode {
        if (errorMessage != null) {
            throw Exception(errorMessage)
        }
        if (!hasNext()) {
            throw NoSuchElementException("No more AST nodes")
        }
        val result = nextNode
        nextNode = null
        return result!!
    }

    private fun fetchNextNode(): ASTNode? {
        return try {
            parser.parse(tokens)
        } catch (e: Exception) {
            errorMessage = e.message
            null
        }
    }
}