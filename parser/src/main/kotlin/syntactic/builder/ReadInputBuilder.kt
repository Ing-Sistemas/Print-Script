package com.printscript.parser.syntactic.builder

import com.printscript.ast.ASTNode
import com.printscript.ast.ReadInputNode
import com.printscript.token.Token
import com.printscript.token.TokenType

class ReadInputBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): ASTNode {
        val input = tokens[1].getValue()
        return ReadInputNode(input, tokens[0].getPosition())
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_INPUT
    }
}