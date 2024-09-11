package org.example.parser.syntactic

import Token
import org.example.parser.syntactic.builder.*
import org.example.token.TokenType

class SyntacticAnalyzer {
    companion object {
        val builderStrategy = listOf<ASTBuilderStrategy>(
            VariableDeclarationBuilder(),
            EmptyVarDecBuilder(),
            FunctionCallBuilder(),
            AssignationBuilder(),
            ExpressionBuilder(),
        )
    }

    fun build(tokens: List<Token>): SyntacticResult {
        if (tokens[tokens.lastIndex].getType() != TokenType.SEMICOLON) return SyntacticFail("Missing ';' at pos: ${tokens.last().getPosition().getColumn()}")
        for (builder in builderStrategy) {
            if (builder.isValidStruct(tokens)) {
                return SyntacticSuccess(builder.build(tokens))
            }
        }
        return SyntacticFail("Invalid syntactic structure at ${tokens.first().getValue()} in: ${tokens.first().getPosition().getLine()} ")
    }
}