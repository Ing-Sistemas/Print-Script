package com.printscript.parser.syntactic.builder

import com.printscript.ast.ReadInputNode
import com.printscript.parser.syntactic.SyntacticResult
import com.printscript.parser.syntactic.SyntacticSuccess
import com.printscript.token.Token
import com.printscript.token.TokenType

class ReadInputBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): SyntacticResult {
        val prompt = tokens[2].getValue()
        return SyntacticSuccess(ReadInputNode(prompt, tokens[0].getPosition()))
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == TokenType.READ_INPUT
    }
}