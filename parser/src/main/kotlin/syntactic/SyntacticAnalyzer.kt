package org.example.parser.syntactic

import Token
import org.example.parser.syntactic.builder.*

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
                return builder.build(tokenList)
            }
        }
        return SyntacticFail(
            "Invalid syntactic structure at: " +
                "${tokenList.first().getPosition().getLine()} " +
                "${tokenList.first().getPosition().getColumn()}",
        )
    }
}