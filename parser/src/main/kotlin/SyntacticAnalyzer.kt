package org.example.parser


import Token
import org.example.*
import org.example.parser.builder.*
import org.example.token.TokenType
import org.example.token.TokenType.*
import java.util.*

class SyntacticAnalyzer {

    companion object {
        const val TEMP_NUM = 1
        val builderStrategy = mutableMapOf<TokenType, ASTBuilderStrategy>(
            KEYWORD to VariableDeclarationBuilder(),
            ASSIGNMENT to AssignationBuilder(),
            CALL to CallBuilder(),
        )
    }

    fun buildAST(tokens: List<Token>): ASTNode {
        val statements = buildStatements(tokens)
        val statementNodes = mutableListOf<ASTNode>()
        for (statement in statements) {
            val firstToken = statement.first()
            val builder = builderStrategy[firstToken.getType()] ?:
            error("token ${firstToken.getType()} not found")
            val astRoot = builder.build(firstToken, statement)
            val statementNode = StatementNode(astRoot, 0,0)
            statementNodes.add(statementNode)
        }
        return ProgramNode(TEMP_NUM, TEMP_NUM, statementNodes)
    }

    private fun buildStatements(tokens: List<Token>): List<List<Token>>{
        val listIt = tokens.iterator()
        val statements = mutableListOf<List<Token>>()
        val statement = mutableListOf<Token>()
        while (listIt.hasNext()){
            val token = listIt.next()
            if(token.getType() == SEMICOLON){
                statement.add(token)
                statements.add(statement.toList())
                statement.clear()
            } else {
                statement.add(token)
            }
        }
        return statements
    }

}