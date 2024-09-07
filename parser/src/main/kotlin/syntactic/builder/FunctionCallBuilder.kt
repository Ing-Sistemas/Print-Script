package org.example.parser.syntactic.builder

import ASTNode
import Token

class FunctionCallBuilder : ASTBuilderStrategy {
    override fun build(tokens: List<Token>): ASTNode {
        TODO("Not yet implemented")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

}
//override fun build(token: Token, tokens: List<Token>): ASTNode {
//        val tokenPos = tokens.indexOf(token)
//        if (!hasParens(tokenPos, tokens)) {
//            error("Call parenthesis not found")
//        }
//        val args = mutableListOf<ASTNode>()
//        val tokensArgs = tokens.subList(tokenPos + 2, tokens.lastIndex - 1) // grabs all the arguments between the () //excluding them
//        if (tokensArgs.size > 1 && isOperator(tokensArgs[1])) {
//            val ast = BinaryExpressionBuilder().build(tokensArgs[1], tokensArgs)
//            args.add(ast)
//        } else {
//            when (val type = tokens[tokenPos + 2].getType()) {
//                // checks the following token inside the ( ), therefore it builds the args list
//                LITERAL_NUMBER -> args.add(LiteralNode(tokens[tokenPos + 2].getValue(), type.name, 0, 0))
//                LITERAL_STRING -> args.add(LiteralNode(tokens[tokenPos + 2].getValue(), type.name, 0, 0))
//                IDENTIFIER -> args.add(IdentifierNode(tokens[tokenPos + 2].getValue(), 0, 0))
//                else -> error("Unexpected token type")
//            }
//        }
//
//        return CallNode(token.getValue(), args, 0, 0)
//    }
//
//    override fun isValidStruct(tokens: List<Token>): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    private fun hasParens(tokenPos: Int, tokens: List<Token>): Boolean {
//        return (
//            tokens[tokenPos + 1].getType() == OPENING_PARENS &&
//                tokens[tokens.lastIndex - 1].getType() == CLOSING_PARENS
//            )
//    }
//
//    private fun isOperator(token: Token): Boolean {
//        return when (token.getType()) {
//            MINUS_OPERATOR -> true
//            DIVIDE_OPERATOR -> true
//            PLUS_OPERATOR -> true
//            MULTIPLY_OPERATOR -> true
//            else -> false
//        }
//    }