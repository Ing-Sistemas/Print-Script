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

        //val currentNode = ProgramNode(TEMP_NUM, TEMP_NUM, statements)
        TODO()
    }
}


//    //TODO idea: create a class Result for parsing
//    //TODO idea: use a map to assign a Token with a AST Builder
//    // add indexes to Token class
//
//    fun buildAST(tokens: List<Token>): ASTNode {
//        val stopAt = SEMICOLON
//        val tokenIterator = tokens.iterator()
//        val currentNode = ProgramNode(TEMP_NUM, TEMP_NUM, buildAST(tokenIterator, stopAt))
//        return currentNode
//    }
//
//    private fun buildAST(tokens: Iterator<Token>, stopAt: TokenType): List<ASTNode>{
//        val astNodes = mutableListOf<ASTNode>()
//
//        while (tokens.hasNext()){
//            val token = tokens.next()
//            when(token.getType()){
//                KEYWORD -> {
//                    when(token.getValue()) {
//                        "let" -> {
//                            val variableDeclaration = VariableDeclarationNode(token,TEMP_NUM,TEMP_NUM, buildAST(tokens, stopAt))
//                            astNodes.add(variableDeclaration)
//                        }
//                        "println" -> {
//                            val callNode = CallNode(token, buildAST(tokens, stopAt))
//                            astNodes.add(callNode)
//                        }
//                    }
//                }
//
//                IDENTIFIER -> {
//                    val identifierNode = IdentifierNode(token,TEMP_NUM,TEMP_NUM)
//                    astNodes.add(identifierNode)
//                }
//
//                COLON -> {
//                    val leafNode = LeafNode(token, TEMP_NUM, TEMP_NUM)
//                    astNodes.add(leafNode)
//                }
//
//                STRING_TYPE -> {
//                    val typeDeclarationNode = TypeDeclarationNode(token,TEMP_NUM,TEMP_NUM)
//                    astNodes.add(typeDeclarationNode)
//                }
//                NUMBER_TYPE -> {
//                    val typeDeclarationNode = TypeDeclarationNode(token,TEMP_NUM,TEMP_NUM)
//                    astNodes.add(typeDeclarationNode)
//                }
//
//                LITERAL_NUMBER -> {
//                    val stringLiteralNode = LiteralNode(token, token.getValue(), TEMP_NUM, TEMP_NUM)
//                    astNodes.add(stringLiteralNode)
//                }
//
//                LITERAL_STRING -> {
//                    val stringLiteralNode = LiteralNode(token, token.getValue(), TEMP_NUM, TEMP_NUM)
//                    astNodes.add(stringLiteralNode)
//                }
//
//                ASSIGNMENT -> {
//                    val assigmentNode = AssignmentNode(token, buildAST(tokens, stopAt))
//                    astNodes.add(assigmentNode)
//                }
//
//                stopAt -> {break}
//
//                else -> {
//                    error("Unexpected token ${token.getType()}")
//                }
//            }
//        }
//        return astNodes
//    }
//}

// --------------------------------------------------------------------------------------------------------------------
//companion object {
//        const val TEMP_NUM = 1
//    }
//
//    fun buildAST(tokens: List<Token>): ASTNode {
//        val stopAt = SEMICOLON
//        val tokenIterator = tokens.iterator()
//        val currentNode = ProgramNode(TEMP_NUM, TEMP_NUM, buildAST(tokenIterator, stopAt))
//        return currentNode
//    }
//
//    private fun buildAST(tokens: Iterator<Token>, stopAt: TokenType): List<ASTNode> {
//        val astNodes = mutableListOf<ASTNode>()
//        while (tokens.hasNext()) {
//            val token = tokens.next()
//            val node = readToken(token, tokens, stopAt)
//            astNodes.add(node)
//            if (token.getType() == stopAt) {
//                break
//            }
//        }
//        return astNodes
//    }
//
//    private fun readToken(token: Token, tokens: Iterator<Token>, stopAt: TokenType): ASTNode {
//        return when (token.getType()) {
//            KEYWORD -> handleKeywordToken(token, tokens, stopAt)
//            IDENTIFIER -> IdentifierNode(token, TEMP_NUM, TEMP_NUM)
//            COLON -> UnaryNode(token, TEMP_NUM, TEMP_NUM)
//            STRING_TYPE -> TypeDeclarationNode(token, TEMP_NUM, TEMP_NUM)
//            NUMBER_TYPE -> TypeDeclarationNode(token, TEMP_NUM, TEMP_NUM)
//            LITERAL_NUMBER -> LiteralNode(token, TEMP_NUM, TEMP_NUM)
//            LITERAL_STRING -> LiteralNode(token, TEMP_NUM, TEMP_NUM)
//            ASSIGNMENT -> AssignmentNode(token, buildAST(tokens, stopAt), buildAST())
//            else -> error("Unexpected token ${token.getType()}")
//        }
//    }
//
//    private fun handleKeywordToken(token: Token, tokens: Iterator<Token>, stopAt: TokenType): ASTNode {
//        return when (token.getValue()) {
//            "let" -> VariableDeclarationNode(token, TEMP_NUM, TEMP_NUM, buildAST(tokens, stopAt))
//            "println" -> CallNode(token, buildAST(tokens, stopAt))
//            else -> error("Unexpected keyword ${token.getValue()}")
//        }
//    }