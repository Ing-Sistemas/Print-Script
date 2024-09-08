package org.example.parser.syntactic.builder

import ASTNode
import Expression
import Token

class BinaryExpressionHelper  {

    fun buildBinaryExpression(tokens : List<Token>) : Expression {
        TODO()
    }

}

//override fun build(token: Token, tokens: List<Token>): AssignmentNode {
//        val tokenIndex = tokens.indexOf(token)
//        val previousToken = tokens[tokenIndex - 1]
//
//        return when (previousToken.getType()) {
//            IDENTIFIER -> handleBinaryValue(tokenIndex, tokens)
//            NUMBER_TYPE -> handleValDeclaration(tokenIndex, tokens)
//            STRING_TYPE -> handleValDeclaration(tokenIndex, tokens)
//            else -> { error("wrong token type ${previousToken.getType()}") }
//        }
//    }
//
//    override fun isValidStruct(tokens: List<Token>): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    private fun handleBinaryValue(tokenIndex: Int, tokens: List<Token>): AssignmentNode {
//        val idToken = tokens[tokenIndex - 1]
//        val valueToken = tokens[tokenIndex + 1]
//        return buildAssigmentNode(tokenIndex, idToken, valueToken, tokens)
//    }
//
//    private fun handleValDeclaration(tokenIndex: Int, tokens: List<Token>): AssignmentNode {
//        val idToken = tokens[tokenIndex - 3]
//        val valueToken = tokens[tokenIndex + 1]
//        return buildAssigmentNode(tokenIndex, idToken, valueToken, tokens)
//    }
//
//    private fun buildAssigmentNode(
//        tokenIndex: Int,
//        idToken: Token,
//        valueToken: Token,
//        tokens: List<Token>,
//    ): AssignmentNode {
//        try {
//            val operator = tokens[tokenIndex + 2]
//            val valueNode = BinaryExpressionBuilder().build(operator, tokens)
//            return AssignmentNode(
//                tokens[tokenIndex].getValue(),
//                IdentifierNode(idToken.getValue(), 0, 0),
//                valueNode,
//                0,
//                0,
//            )
//        } catch (e: Exception) {
//            val idNode = IdentifierNode(idToken.getValue(), 0, 0)
//            val valueNode = buildValueLeaf(valueToken)
//            return AssignmentNode(tokens[tokenIndex].getValue(), idNode, valueNode, 0, 0)
//        }
//    }
//
//    private fun buildValueLeaf(valueToken: Token): ASTNode {
//        return when (valueToken.getType()) {
//            IDENTIFIER -> IdentifierNode(valueToken.getValue(), 0, 0)
//            LITERAL_NUMBER -> LiteralNode(valueToken.getValue(), valueToken.getType().name, 0, 0)
//            LITERAL_STRING -> LiteralNode(valueToken.getValue(), valueToken.getType().name, 0, 0)
//            else -> { error("wrong token type ${valueToken.getType()}") }
//        }
//    }