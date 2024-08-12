package org.example.parser.builder

import Token
import org.example.ASTNode
import org.example.VariableDeclarationNode

class VariableDeclarationBuilder: ASTBuilderStrategy {
    override fun build(token: Token, tokens: ListIterator<Token>): ASTNode {

        TODO()
        //return VariableDeclarationNode(token, 0,0,)
    }
}