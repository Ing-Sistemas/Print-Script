package org.example.parser.syntactic.builder

import Token
import org.example.ASTNode
import org.example.CallNode
import org.example.IdentifierNode
import org.example.LiteralNode
import org.example.token.TokenType

class CallBuilder: ASTBuilderStrategy {
    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val tokenPos = tokens.indexOf(token)
        println(tokens[tokenPos + 2].getType())
        println(tokens[tokens.lastIndex - 2].getType())
        if(!hasParens(tokenPos, tokens)){
            error("Call parenthesis not found")
        }
        val args = mutableListOf<ASTNode>()
        when(val type = tokens[tokenPos+ 2].getType()){
            TokenType.LITERAL_NUMBER ->args.add( LiteralNode(tokens[tokenPos+ 2].getValue(),type.name ,0,0))
            TokenType.LITERAL_STRING ->args.add(LiteralNode(tokens[tokenPos + 2].getValue(), type.name,0,0))
            TokenType.IDENTIFIER -> args.add(IdentifierNode(tokens[tokenPos + 2].getValue(), 0,0))
            else -> error("Unexpected token type")
        }
        return CallNode(token.getValue(), args, 0,0)
    }
    private fun hasParens(tokenPos: Int, tokens: List<Token>): Boolean{
        return (tokens[tokenPos + 1].getType() == TokenType.OPENING_PARENS
                && tokens[tokens.lastIndex - 1].getType() == TokenType.CLOSING_PARENS)
    }
}