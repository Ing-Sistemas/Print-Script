package org.example.parser.syntactic

import Token
import org.example.*
import org.example.parser.syntactic.builder.ASTBuilderStrategy
import org.example.parser.syntactic.builder.AssignationBuilder
import org.example.parser.syntactic.builder.FunctionCallBuilder
import org.example.parser.syntactic.builder.VariableDeclarationBuilder
import org.example.token.TokenType
import org.example.token.TokenType.*

class SyntacticAnalyzer {

    companion object {
        const val TEMP_NUM = 1

        val builderStrategy = mutableMapOf<TokenType, ASTBuilderStrategy>(
            KEYWORD to VariableDeclarationBuilder(),
            IDENTIFIER to AssignationBuilder(),
            CALL to FunctionCallBuilder(),
        )
    }

    fun buildAST(tokens: List<Token>): SyntacticResult {
        try {
            val statements = buildStatements(tokens)
            val statementNodes = mutableListOf<ASTNode>() // list of nodes that will be ProgramNode's children

            for (statement in statements) {
                val firstToken = statement.first() // used for identifying which strategy to use
                val builder = builderStrategy[firstToken.getType()]
                    ?: error("token ${firstToken.getType()} not found")
                val astRoot = when (firstToken.getType()) {
                    IDENTIFIER -> {
                        val equalTokenIndex = statement[1]
                        builder.build(equalTokenIndex, statement)
                    }
                    KEYWORD -> builder.build(firstToken, statement)
                    CALL -> builder.build(firstToken, statement)
                    else -> error("token ${firstToken.getType()} not found")
                }
                val statementNode = StatementNode(astRoot, 0, 0)
                statementNodes.add(statementNode)
            }
            return SyntacticSuccess(ProgramNode(TEMP_NUM, TEMP_NUM, statementNodes))
        } catch (e: Exception) {
            return SyntacticFail(e.message ?: e.toString())
        }
    }

    private fun buildStatements(tokens: List<Token>): List<List<Token>> {
        val listIt = tokens.iterator()
        val statements = mutableListOf<List<Token>>()
        val statement = mutableListOf<Token>()
        while (listIt.hasNext()) {
            val token = listIt.next()
            if (token.getType() == SEMICOLON) {
                statement.add(token)
                statements.add(statement.toList())
                statement.clear()
            } else {
                statement.add(token)
            }
        }
        if (statement.isNotEmpty()) {
            error("; is missing")
        }
        return statements
    }
}