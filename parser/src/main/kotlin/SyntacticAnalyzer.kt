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
        val firstToken = tokens.first()
        val builder = builderStrategy[firstToken.getType()] ?:
        error("token ${firstToken.getType()} not found")
        val node = builder.build(firstToken, tokens)
        return ProgramNode(TEMP_NUM, TEMP_NUM, listOf(node))
    }

}