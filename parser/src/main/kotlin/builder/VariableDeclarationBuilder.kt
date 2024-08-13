package org.example.parser.builder

import Token
import org.example.ASTNode
import org.example.TypeDeclarationNode
import org.example.VariableDeclarationNode
import org.example.token.TokenType

class VariableDeclarationBuilder: ASTBuilderStrategy {

    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val tokenPos = tokens.indexOf(token)
        val valDec = tokens[tokenPos + 3] //todo remove, too suspicious
        if (isNotTypeDeclaration(valDec)) {
            error("Variable declaration expected")
        }
        return VariableDeclarationNode(
            token,
            0,
            0,
            TypeDeclarationNode(valDec, 0,0),
            AssignationBuilder().build(tokens[tokenPos+ 4], tokens)
            )
    }

    private fun isNotTypeDeclaration(token: Token): Boolean {
        return when(token.getType()){
            TokenType.NUMBER_TYPE -> false
            TokenType.STRING_TYPE -> false
            else -> true
        }
    }
}