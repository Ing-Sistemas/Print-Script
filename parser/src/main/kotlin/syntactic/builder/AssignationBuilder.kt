package org.example.parser.syntactic.builder

import Token
import org.example.token.TokenType.*
import java.lang.Exception

class AssignationBuilder : ASTBuilderStrategy {
    /**
     * Returns an AssigmentNode with the structure
     *
     *                      =
     *
     *                  /       \
     *
     *       identifierNode     valueNode
     *
     *  `Note: the valueNode is everything that follows the equal sign (either a Literal, variable or binary Operation)`
     *
     *  The token received is the ASSIGMENT token, ('='), so all the indexes are based on the position this token occupies on the List
     */

    override fun build(token: Token, tokens: List<Token>): AssignmentNode {
        val tokenIndex = tokens.indexOf(token)
        val previousToken = tokens[tokenIndex - 1]

        return when (previousToken.getType()) {
            IDENTIFIER -> handleBinaryValue(tokenIndex, tokens)
            NUMBER_TYPE -> handleValDeclaration(tokenIndex, tokens)
            STRING_TYPE -> handleValDeclaration(tokenIndex, tokens)
            else -> { error("wrong token type ${previousToken.getType()}") }
        }
    }

    /**
     * Two types of assigment: inside a varDeclarationNode or directly in the statement (a = 5;)
     *
     *  Therefore, two handlers for each situation
     *
     *  Each situation has different handlers because of the different structure of the given token List
     *
     *  (the indexes vary from one to another)
     *
     *  [handleBinaryValue] follows the structure of `a = value;` with its token List and indexes
     */

    private fun handleBinaryValue(tokenIndex: Int, tokens: List<Token>): AssignmentNode {
        val idToken = tokens[tokenIndex - 1]
        val valueToken = tokens[tokenIndex + 1]
        return buildAssigmentNode(tokenIndex, idToken, valueToken, tokens)
    }
    // token list example on binary value

    /**
     * [handleValDeclaration] follows the structure `let a:string = 'hi';`
     */

    private fun handleValDeclaration(tokenIndex: Int, tokens: List<Token>): AssignmentNode {
        val idToken = tokens[tokenIndex - 3]
        val valueToken = tokens[tokenIndex + 1]
        return buildAssigmentNode(tokenIndex, idToken, valueToken, tokens)
    }

    private fun buildAssigmentNode(
        tokenIndex: Int,
        idToken: Token,
        valueToken: Token,
        tokens: List<Token>,
    ): AssignmentNode {
        try {
            val operator = tokens[tokenIndex + 2]
            val valueNode = BinaryNodeBuilder().build(operator, tokens)
            return AssignmentNode(
                tokens[tokenIndex].getValue(),
                IdentifierNode(idToken.getValue(), 0, 0),
                valueNode,
                0,
                0,
            )
        } catch (e: Exception) {
            val idNode = IdentifierNode(idToken.getValue(), 0, 0)
            val valueNode = buildValueLeaf(valueToken)
            return AssignmentNode(tokens[tokenIndex].getValue(), idNode, valueNode, 0, 0)
        }
    }

    private fun buildValueLeaf(valueToken: Token): ASTNode {
        return when (valueToken.getType()) {
            IDENTIFIER -> IdentifierNode(valueToken.getValue(), 0, 0)
            LITERAL_NUMBER -> LiteralNode(valueToken.getValue(), valueToken.getType().name, 0, 0)
            LITERAL_STRING -> LiteralNode(valueToken.getValue(), valueToken.getType().name, 0, 0)
            else -> { error("wrong token type ${valueToken.getType()}") }
        }
    }

    // token list example on variable assignation
    // [KEYWORD, IDENTIFIER, COLON, STRING_TYPE, ASSIGNATION, STRING_LITERAL, SEMICOLON]
}