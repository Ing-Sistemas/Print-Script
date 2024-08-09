package org.example.parser

import com.sun.tools.example.debug.expr.ExpressionParserConstants.STRING_LITERAL
import org.example.ast.*
import org.example.token.Token
import org.example.token.TokenType
import org.example.token.TokenType.*

class SyntacticAnalyser {
    companion object{
        const val TEMP_NUM = 1
    }

    //TODO idea: use a map to assign a Token with a AST Builder
    // add indexes to Token class

    fun buildAST(tokens: List<Token>): ASTNode{
        val stopAt = SEMICOLON
        val tokenIterator = tokens.iterator()
        val currentNode = ProgramNode(TEMP_NUM, TEMP_NUM, buildAST(tokenIterator, stopAt))
        return currentNode
    }

    private fun buildAST(tokens: Iterator<Token>, stopAt: TokenType): List<ASTNode>{
        val astNodes = mutableListOf<ASTNode>()

        while (tokens.hasNext()){
            val token = tokens.next()
            when(token.getType()){
                KEYWORD -> {
                    when(token.getValue()) {
                        "let" -> {
                            val variableDeclaration = VariableDeclarationNode(token,TEMP_NUM,TEMP_NUM, buildAST(tokens, stopAt))
                            astNodes.add(variableDeclaration)
                        }
                        "println" -> {
                            val callNode = CallNode(token, buildAST(tokens, stopAt))
                            astNodes.add(callNode)
                        }
                    }
                }

                IDENTIFIER -> {
                    val identifierNode = IdentifierNode(token,TEMP_NUM,TEMP_NUM)
                    astNodes.add(identifierNode)
                }

                COLON -> {
                    val leafNode = LeafNode(token)
                    astNodes.add(leafNode)
                }

                STRING_TYPE -> {
                    val typeDeclarationNode = TypeDeclarationNode(token,TEMP_NUM,TEMP_NUM)
                    astNodes.add(typeDeclarationNode)
                }
                NUMBER_TYPE -> {
                    val typeDeclarationNode = TypeDeclarationNode(token,TEMP_NUM,TEMP_NUM)
                    astNodes.add(typeDeclarationNode)
                }

                LITERAL_NUMBER -> {
                    val stringLiteralNode = LiteralNode(token, token.getValue(), TEMP_NUM, TEMP_NUM)
                    astNodes.add(stringLiteralNode)
                }

                LITERAL_STRING -> {
                    val stringLiteralNode = LiteralNode(token, token.getValue(), TEMP_NUM, TEMP_NUM)
                    astNodes.add(stringLiteralNode)
                }

                ASSIGNMENT -> {
                    val assigmentNode = AssignmentNode(token, buildAST(tokens, stopAt))
                    astNodes.add(assigmentNode)
                }

                stopAt -> {break}

                else -> {
                    error("Unexpected token ${token.getType()}")
                }
            }
        }
        return astNodes
    }

}