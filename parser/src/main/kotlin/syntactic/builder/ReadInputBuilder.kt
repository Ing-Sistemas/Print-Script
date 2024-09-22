package org.example.parser.syntactic.builder

import ASTNode
import ReadInputNode
import Token
import org.example.token.TokenType

class ReadInputBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): ASTNode {
        val input = tokens[1].getValue()
        return ReadInputNode(input, tokens[0].getPosition())
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_INPUT
    }
}