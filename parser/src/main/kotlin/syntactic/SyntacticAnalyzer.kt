package com.printscript.parser.syntactic

import com.printscript.parser.syntactic.builder.*
import com.printscript.parser.syntactic.builder.ASTBuilderStrategy
import com.printscript.token.Token

class SyntacticAnalyzer {
    private val builderStrategy = listOf<ASTBuilderStrategy>(
        VariableDeclarationBuilder(),
        EmptyVarDecBuilder(),
        FunctionCallBuilder(),
        AssignationBuilder(),
        IfStatementBuilder(),
        ReadEnvBuilder(),
        ReadInputBuilder(),
        ExpressionBuilder(),
    )

    fun build(tokens: Iterator<Token>): SyntacticResult {
        val tokenList = TokenAccumulator().getTokens(tokens)
        for (builder in builderStrategy) {
            if (builder.isValidStruct(tokenList)) {
                return SyntacticSuccess(builder.build(tokenList))
            }
        }
        return SyntacticFail("Invalid syntactic structure at ${tokenList.first().getValue()} in: ${tokenList.first().getPosition().getLine()} ")
    }
}