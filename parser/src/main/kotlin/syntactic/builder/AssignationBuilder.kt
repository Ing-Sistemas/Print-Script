package org.example.parser.syntactic.builder

import Token
import org.example.ASTNode
import org.example.AssignmentNode
import org.example.IdentifierNode
import org.example.LiteralNode
import org.example.parser.syntactic.Error
import org.example.parser.syntactic.SyntacticResult
import org.example.token.TokenType.*
import java.lang.Exception

class AssignationBuilder: ASTBuilderStrategy {

    /**
     * Returns the assigment node
     */

    override fun build(token: Token, tokens: List<Token>): AssignmentNode  {
        val tokenIndex = tokens.indexOf(token)
        val previousToken = tokens[tokenIndex - 1]

        return when (previousToken.getType()) {
            IDENTIFIER -> handleBinaryValue(tokenIndex,tokens)
            NUMBER_TYPE -> handleValDeclaration(tokenIndex,tokens)
            STRING_TYPE -> handleValDeclaration(tokenIndex,tokens)
            else -> { error("wrong token type ${previousToken.getType()}") }
        }
    }

    private fun handleBinaryValue(tokenIndex: Int, tokens: List<Token>): AssignmentNode{
        val idToken = tokens[tokenIndex - 1]
        val valueToken = tokens[tokenIndex + 1]
        try {
            val operator = tokens[tokenIndex + 2]
            val valueNode = BinaryNodeBuilder().build(operator, tokens)
            return AssignmentNode(
                tokens[tokenIndex].getValue(),
                IdentifierNode(idToken.getValue(),0,0),
                valueNode,
                0,0
            )
        } catch (e: Exception) {
            val idNode = IdentifierNode(idToken.getValue(), 0,0)
            val valueNode = LiteralNode(valueToken.getValue(), valueToken.getType().name,0,0)
            return AssignmentNode(tokens[tokenIndex].getValue(), idNode,valueNode, 0,0)
        }
    }

    private fun handleValDeclaration(tokenIndex: Int, tokens: List<Token>): AssignmentNode{
        val typeDecToken = tokens[tokenIndex - 1]
        val idToken = tokens[tokenIndex - 3]
        val valueToken = tokens[tokenIndex + 1]
        return AssignmentNode(
            tokens[tokenIndex].getValue(),
            IdentifierNode(idToken.getValue(),0,0),
            LiteralNode(valueToken.getValue(), typeDecToken.getType().name,0 ,0),
            0,
            0
        )
    }
}