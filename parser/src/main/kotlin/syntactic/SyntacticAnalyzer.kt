package com.printscript.parser.syntactic

import com.printscript.parser.syntactic.builder.*
import com.printscript.parser.syntactic.builder.ASTBuilderStrategy
import com.printscript.token.Token
import com.printscript.token.TokenType

class SyntacticAnalyzer() {
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
        val tokenList = getTokenList(tokens)
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

    private var currentList = mutableListOf<Token>()
    private val pushedBackTokens = mutableListOf<Token>()

    private fun getTokenList(tokens: Iterator<Token>): List<Token> {
        while (tokens.hasNext() || pushedBackTokens.isNotEmpty()) {
            val token = if (pushedBackTokens.isNotEmpty()) {
                pushedBackTokens.removeAt(pushedBackTokens.size - 1)
            } else {
                tokens.next()
            }
            when (token.getType()) {
                TokenType.IF -> {
                    return handleIfElseBlock(token, tokens)
                }
                TokenType.SEMICOLON -> {
                    currentList.add(token)
                    val result = currentList.toList()
                    currentList.clear()
                    return result
                }
                else -> {
                    currentList.add(token)
                }
            }
        }

        return if (currentList.isNotEmpty()) {
            currentList.toList().also { currentList.clear() }
        } else {
            emptyList()
        }
    }

    private fun handleIfElseBlock(ifToken: Token, tokens: Iterator<Token>): List<Token> {
        val tokenList = mutableListOf<Token>()
        tokenList.add(ifToken)

        while (tokens.hasNext()) {
            val token = tokens.next()
            tokenList.add(token)

            if (token.getType() == TokenType.CLOSING_BRACES) {
                break
            }
        }

        if (tokens.hasNext()) {
            val nextToken = tokens.next()
            if (nextToken.getType() == TokenType.ELSE) {
                tokenList.add(nextToken)
                while (tokens.hasNext()) {
                    val elseToken = tokens.next()
                    tokenList.add(elseToken)
                    if (elseToken.getType() == TokenType.CLOSING_BRACES) {
                        break
                    }
                }
            } else {
                pushedBackTokens.add(nextToken)
            }
        }
        return tokenList
    }
}