package org.example.parser.syntactic.builder

import ReadEnvNode
import Token
import org.example.parser.syntactic.SyntacticResult
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType

class ReadEnvBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): SyntacticResult {
        val identifierName = tokens[1].getValue()
        val position = tokens[1].getPosition()
        return SyntacticSuccess(ReadEnvNode(identifierName, position))
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_ENV &&
            tokens[1].getType() == TokenType.OPENING_PARENS &&
            tokens[2].getType() == TokenType.IDENTIFIER
    }
}