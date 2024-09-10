package org.example.parser.syntactic.builder

import ASTNode
import FunctionCallStatement
import Token
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType.*

class FunctionCallBuilder : ASTBuilderStrategy {

    private val expectedStruct = listOf(
        CALL,
        OPENING_PARENS, // the parenthesis enclose the argument
        CLOSING_PARENS,
        OPENING_CURLY_BRACKS, // the {} enclose de body of function
        CLOSING_CURLY_BRACKS,
    )

    override fun build(tokens: List<Token>): FunctionCallStatement {
        val tokenIterator = tokens.listIterator()
        return parseFunCall(tokenIterator) ?: throw Exception("Failed to parse function call")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[expectedStruct.indexOf(CALL)].getType() == CALL &&
            tokens[expectedStruct.indexOf(OPENING_PARENS)].getType() == OPENING_PARENS
    }

    private fun parseFunCall(tokens: ListIterator<Token>): FunctionCallStatement {

        val funCallToken = tokens.next()
        tokens.next()//consume (

        val arguments = mutableListOf<Token>()
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == OPENING_CURLY_BRACKS) {
                break
            }
            if (token.getType() != CLOSING_PARENS) {
                arguments.add(token)
            } else continue
        }
        val args = ExpressionBuilder().build(arguments)

        val bodyList = mutableListOf<Token>()
        val body = mutableListOf<ASTNode>()
        if (tokens.hasNext()) {
            while (tokens.hasNext()) {
                val nextToken = tokens.next()
                if (nextToken.getType() == CLOSING_CURLY_BRACKS) {
                    if(tokens.hasNext()) {
                        tokens.next()
                    }
                    break
                }
                bodyList.add(nextToken)
            }
            val result = SyntacticAnalyzer().build(bodyList)
            if (result is SyntacticSuccess) {
                body.add(result.astNode)
            } else throw Exception("Failed to parse function call")
        }
        return FunctionCallStatement(funCallToken.getValue(), listOf(args), body,)
    }
}
