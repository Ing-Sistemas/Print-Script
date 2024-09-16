package org.example.parser.syntactic

import Token
import org.example.parser.syntactic.builder.*
import org.example.token.TokenType

class SyntacticAnalyzer {
    private val builderStrategy = listOf<ASTBuilderStrategy>(
        VariableDeclarationBuilder(),
        EmptyVarDecBuilder(),
        FunctionCallBuilder(),
        AssignationBuilder(),
        ExpressionBuilder(),
    )

    fun build(tokens: Iterator<Token>): SyntacticResult {
        val tokenList = TokenAccumulator().getTokens(tokens)

        if (tokenList[tokenList.lastIndex].getType() != TokenType.SEMICOLON) return SyntacticFail("Missing ';' at pos: ${tokenList.last().getPosition().getColumn()}")
        for (builder in builderStrategy) {
            if (builder.isValidStruct(tokenList)) {
                return SyntacticSuccess(builder.build(tokenList))
            }
        }
        return SyntacticFail("Invalid syntactic structure at ${tokenList.first().getValue()} in: ${tokenList.first().getPosition().getLine()} ")
    }
}