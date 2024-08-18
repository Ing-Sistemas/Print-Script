package org.example.parser.syntactic.builder

import Token
import org.example.ASTNode
import org.example.BinaryNode
import org.example.IdentifierNode
import org.example.LiteralNode
import org.example.token.TokenType
import org.example.token.TokenType.*

class BinaryNodeBuilder: ASTBuilderStrategy {

    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val operatorIndex = tokens.indexOf(token)
        val left = tokens[operatorIndex - 1]

        val possibleOperator = tokens[operatorIndex + 2]
        val right = if(isOperator(possibleOperator)){
            build(possibleOperator, tokens)
        } else { buildNode(tokens[operatorIndex + 1])}
        return BinaryNode(token.getValue(), buildNode(left), right, 0,0)
    }

    private fun buildNode(token: Token): ASTNode{
        return when(token.getType()){
            LITERAL_STRING -> LiteralNode(token.getValue(), token.getType().name, 0,0)
            LITERAL_NUMBER -> LiteralNode(token.getValue(), token.getType().name, 0,0)
            IDENTIFIER -> IdentifierNode(token.getValue(), 0,0)
            else -> throw Exception("Unexpected token type: ${token.getType()}")
        }
    }
    private fun isOperator(token: Token): Boolean {
        return when (token.getType()) {
            MINUS_OPERATOR -> true
            DIVIDE_OPERATOR -> true
            PLUS_OPERATOR -> true
            MULTIPLY_OPERATOR -> true
            else -> false
        }
    }
}