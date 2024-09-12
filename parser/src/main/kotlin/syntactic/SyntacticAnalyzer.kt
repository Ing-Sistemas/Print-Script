package org.example.syntactic

import org.example.*
import org.example.TokenType.*
import org.example.syntactic.builder.ASTBuilderStrategy
import org.example.syntactic.builder.AssignationBuilder
import org.example.syntactic.builder.CallBuilder
import org.example.syntactic.builder.VariableDeclarationBuilder

class SyntacticAnalyzer {
    // TODO remove TEMP_NUM
    /**
     * [TEMP_NUM] current temporary solution to Lexer's issue with indexes
     *
     * [builderStrategy] map of every builder that has as key the type of token assigned
     *
     * {token_type} : builder for that token
     */

    companion object {
        const val TEMP_NUM = 1

        val builderStrategy = mutableMapOf<TokenType, ASTBuilderStrategy>(
            KEYWORD to VariableDeclarationBuilder(),
            IDENTIFIER to AssignationBuilder(),
            CALL to CallBuilder(),
        )
    }

    /**
     * [buildAST]: receives a List<Token> and builds the AST with the builders assigned for each token
     *
     * Returns a Program node that contains as children a list of Statements that are single nodes that point
     * to the ASTNodes build in the previous step
     *
     * Example:
     *  `ProgramNode:{
     *      list of(
     *      Statement : {VarDeclaration},
     *      Statement : {println call}
     *      )
     *  }`
     *
     *
     *  A single statement is every declaration that ends with ';'
     *
     *  Example => `let a:number = 12;`
     */

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

    /**
     * [buildStatements] receives a token list and separates each statement
     * as a list of tokens that end with ';'
     *
     * returns a list containing each statement as a list
     */

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