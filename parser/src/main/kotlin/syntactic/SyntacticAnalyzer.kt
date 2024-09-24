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
        val tokenList = TokenAccumulator(tokens).next()
        for (builder in builderStrategy) {
            if (builder.isValidStruct(tokenList)) {
                return builder.build(tokenList)
            }
        }
        return SyntacticFail(
            "Invalid syntactic structure at: " +
                "line: ${tokenList.first().getPosition().getLine()} ",
        )
    }
}