package org.example.parser.builder

import Token
import org.example.ASTNode
import org.example.AssignmentNode
import org.example.IdentifierNode
import org.example.LiteralNode

class AssignationBuilder: ASTBuilderStrategy {

    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val tokenPos = tokens.indexOf(token) //3
        return AssignmentNode(
            token,
            IdentifierNode(tokens[tokenPos - 3],0,0),
            LiteralNode(tokens[tokenPos + 1], 0 ,0),
            0,0
        )
    }
}