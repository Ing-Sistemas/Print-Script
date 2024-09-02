package org.example.parser.syntactic.builder

import Token
import org.example.token.TokenType

class VariableDeclarationBuilder : ASTBuilderStrategy {
    /**
     * return a variable declaration node with the structure
     *
     *                     Keyword 'let'
     *                   /            \
     *        TypeDeclarationNode       AssigmentNode
     *
     *  `check assigmentBuilder for its structure`
     *
     *  The token received is the KEYWORD let at the beginning of the list, and uses its index to follow the structure
     */
    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val tokenPos = tokens.indexOf(token)
        val valDec = tokens[tokenPos + 3] // todo remove, too suspicious
        if (isNotTypeDeclaration(valDec)) {
            error("Variable declaration expected")
        }
        return VariableDeclarationNode(
            token.getValue(),
            TypeDeclarationNode(valDec.getValue(), 0, 0),
            AssignationBuilder().build(tokens[tokenPos + 4], tokens),
            0,
            0,
        )
    }

    // checks for the valid types in declaration
    private fun isNotTypeDeclaration(token: Token): Boolean {
        return when (token.getType()) {
            TokenType.NUMBER_TYPE -> false
            TokenType.STRING_TYPE -> false
            else -> true
        }
    }
}