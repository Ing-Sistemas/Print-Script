package com.printscript.parser.syntactic.builder

import com.printscript.ast.ReadEnvNode
import com.printscript.parser.syntactic.SyntacticResult
import com.printscript.parser.syntactic.SyntacticSuccess
import com.printscript.token.Token
import com.printscript.token.TokenType

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