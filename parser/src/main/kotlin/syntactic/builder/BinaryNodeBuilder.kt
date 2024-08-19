package org.example.parser.syntactic.builder

import Token
import org.example.ASTNode
import org.example.BinaryNode
import org.example.IdentifierNode
import org.example.LiteralNode
import org.example.token.TokenType
import org.example.token.TokenType.*

class BinaryNodeBuilder: ASTBuilderStrategy {
    /**
     * returns a Binary node with the structure
     *
     *                  Operand
     *
     *               /           \
     *
     *        Value                  Value
     *
     *  The token received is an operator ( +, -, /, *) and all the indexes are relative to the
     *  position of this token in the list
     *
     *
     *  if the value found on the right node is followed by another operand,
     *  then the builder uses this node as a left node and the following operand as the root of the next branch
     *
     *
     *  (leftValue, operand, rightValue, nextOperand, nextValue)
     *  in this case, the next operand is built into another BinaryNode, grabs the rightValue and
     *  uses it as its left value; and the next value is used as a right one
     *
     *  `check final structure at the end`
     */


    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val operatorIndex = tokens.indexOf(token)
        val left = tokens[operatorIndex - 1] // leftValue, operand, rightValue

        val possibleOperator = tokens[operatorIndex + 2] //Checks if there's another operand to build
        val right = if(isOperator(possibleOperator)){   // its branch on the right node from the root
            build(possibleOperator, tokens) //if it finds one, the function builds the branch with recursion
        } else { buildNode(tokens[operatorIndex + 1])} //if the is no operand, that means the following value is a leaf
        return BinaryNode(token.getValue(), buildNode(left), right, 0,0)
    }

    /**
     [buildNode] creates a new Leaf Node based on the given token
     */
    private fun buildNode(token: Token): ASTNode{
        return when(token.getType()){
            LITERAL_STRING -> LiteralNode(token.getValue(), token.getType().name, 0,0)
            LITERAL_NUMBER -> LiteralNode(token.getValue(), token.getType().name, 0,0)
            IDENTIFIER -> IdentifierNode(token.getValue(), 0,0)
            else -> throw Exception("Unexpected token type: ${token.getType()}")
        }
    }

    //checks if a given token is an operator
    private fun isOperator(token: Token): Boolean {
        return when (token.getType()) {
            MINUS_OPERATOR -> true
            DIVIDE_OPERATOR -> true
            PLUS_OPERATOR -> true
            MULTIPLY_OPERATOR -> true
            else -> false
        }
    }

    /*
                    operand
           leftVal            nextOperand
                        rightVal          nextValue

        the original rightVal ends up being the left value of the new node
     */

}