package org.example.parser.syntactic.builder

import ReadInputNode
import Token
import org.example.parser.syntactic.SyntacticResult
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType

class ReadInputBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): SyntacticResult {
        val input = tokens[1].getValue()
        return SyntacticSuccess(ReadInputNode(input, tokens[0].getPosition()))
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_INPUT
    }
}