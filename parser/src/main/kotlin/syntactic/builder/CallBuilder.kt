package org.example.parser.syntactic.builder

import Token
import org.example.ASTNode
import org.example.CallNode
import org.example.IdentifierNode
import org.example.LiteralNode
import org.example.token.TokenType

class CallBuilder: ASTBuilderStrategy {
    /**
     * return a CallNode with a list of ASTs as arguments
     * the node is a call for a specified function, such as println(), if(), for() ...
     *
     *          CallNode: {
     *          args[]
     *          }
     *
     *  current format follows the println(args) structure only and the given token is the `println call`
     *
     *  this call is set in the first index in the list, followed by the token `(`, then `args`, `)` and `;`
     */

    override fun build(token: Token, tokens: List<Token>): ASTNode {
        val tokenPos = tokens.indexOf(token)
        if(!hasParens(tokenPos, tokens)){
            error("Call parenthesis not found")
        }
        val args = mutableListOf<ASTNode>()
        when(val type = tokens[tokenPos+ 2].getType()){
            //TODO, add binary node for println(5 + 5)
            //checks the following token inside the ( ), therefore it builds the args list
            TokenType.LITERAL_NUMBER ->args.add(LiteralNode(tokens[tokenPos+ 2].getValue(),type.name ,0,0))
            TokenType.LITERAL_STRING ->args.add(LiteralNode(tokens[tokenPos + 2].getValue(), type.name,0,0))
            TokenType.IDENTIFIER -> args.add(IdentifierNode(tokens[tokenPos + 2].getValue(), 0,0))
            else -> error("Unexpected token type")
        }
        return CallNode(token.getValue(), args, 0,0)
    }

    /**
     * checks of the call println has `(` `)`
     */

    private fun hasParens(tokenPos: Int, tokens: List<Token>): Boolean{
        return (tokens[tokenPos + 1].getType() == TokenType.OPENING_PARENS
                && tokens[tokens.lastIndex - 1].getType() == TokenType.CLOSING_PARENS)
    }
}