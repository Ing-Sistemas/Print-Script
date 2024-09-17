package org.example.parser.syntactic.builder

import ASTNode
import Expression
import FunctionCallStatement
import Token
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticSuccess
import org.example.token.TokenType.*

class FunctionCallBuilder : ASTBuilderStrategy {
    private val expectedStruct = listOf(
        FUNCTION_CALL,
        OPENING_PARENS, // the parenthesis enclose the argument
        CLOSING_PARENS,
        OPENING_CURLY_BRACKS, // the {} enclose de body of function
        CLOSING_CURLY_BRACKS,
    )
    override fun build(tokens: List<Token>): FunctionCallStatement {
        return parseFunCall(tokens.listIterator())
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[expectedStruct.indexOf(FUNCTION_CALL)].getType() == FUNCTION_CALL &&
            tokens[expectedStruct.indexOf(OPENING_PARENS)].getType() == OPENING_PARENS
    }

    private fun parseFunCall(tokens: ListIterator<Token>): FunctionCallStatement {
        val funCallToken = tokens.next()
        val arguments = handleArgs(tokens)
        val block = handleBlock(tokens)
        return FunctionCallStatement(funCallToken.getValue(), arguments, block, funCallToken.getPosition())
    }

    private fun handleArgs(tokens: ListIterator<Token>): List<Expression> {
        tokens.next() // consumes the (
        val arguments = mutableListOf<Token>()
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == CLOSING_PARENS) {
                break
            } else { arguments.add(token) }
        }
        return listOf(ExpressionBuilder().build(arguments)) // TODO, (arg1,arg2,arg3) are not considered yet
    }

    private fun handleBlock(tokens: ListIterator<Token>): List<ASTNode>? {
        val token = tokens.next()
        if (token.getType() == SEMICOLON) {
            return null
        }
        val bodyTokens = mutableListOf<Token>()
        val body = mutableListOf<ASTNode>()
        while (tokens.hasNext()) {
            val nextToken = tokens.next()
            if (nextToken.getType() == CLOSING_CURLY_BRACKS) {
                if (tokens.hasNext()) {
                    tokens.next()
                }
                break
            }
            bodyTokens.add(nextToken)
        }
        val result = SyntacticAnalyzer().build(bodyTokens.iterator())
        if (result is SyntacticSuccess) {
            body.add(result.astNode)
        } else {
            throw Exception("Failed to parse function call")
        }
        return body
    }
}