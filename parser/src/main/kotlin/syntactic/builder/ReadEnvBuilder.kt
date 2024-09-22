package org.example.parser.syntactic.builder

import ASTNode
import ReadEnvNode
import Token
import org.example.token.TokenType

class ReadEnvBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): ASTNode {
        val identifierName = tokens[1].getValue()
        val position = tokens[1].getPosition()
        return ReadEnvNode(identifierName, position)
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_ENV &&
            tokens[1].getType() == TokenType.OPENING_PARENS &&
            tokens[2].getType() == TokenType.IDENTIFIER
    }
}