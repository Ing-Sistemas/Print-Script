package org.example.parser.syntactic.builder

import Token
import org.example.token.TokenType.*

class BinaryExpressionBuilder : ASTBuilderStrategy {

    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val operatorIndex = tokens.indexOf(token)
        val left = tokens[operatorIndex - 1] // leftValue, operand, rightValue
        val possibleOperator = if (operatorIndex + 2 < tokens.size) tokens[operatorIndex + 2] else null // Checks if there's another operand to build
        val right = if (possibleOperator != null && isOperator(possibleOperator)) { // its branch on the right node from the root
            build(possibleOperator, tokens) // if it finds one, the function builds the branch with recursion
        } else { buildNode(tokens[operatorIndex + 1]) } // if the is no operand, that means the following value is a leaf
        return BinaryNode(token.getValue(), buildNode(left), right, 0, 0)
    }


    private fun buildNode(token: Token): ASTNode {
        return when (token.getType()) {
            LITERAL_STRING -> LiteralNode(token.getValue(), token.getType().name, 0, 0)
            LITERAL_NUMBER -> LiteralNode(token.getValue(), token.getType().name, 0, 0)
            IDENTIFIER -> IdentifierNode(token.getValue(), 0, 0)
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