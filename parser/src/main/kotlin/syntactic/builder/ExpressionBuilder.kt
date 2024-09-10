package org.example.parser.syntactic.builder


import BinaryExpression
import Expression
import IdentifierExpression
import NumberLiteral
import Token
import UnaryExpression
import org.example.token.TokenType.*

class ExpressionBuilder: ASTBuilderStrategy {
    companion object {
        val PRECEDENCE = mapOf(
            PLUS_OPERATOR to 1,
            MINUS_OPERATOR to 1,
            MULTIPLY_OPERATOR to 2,
            DIVIDE_OPERATOR to 2
        )
    }

    private val expectedTypes = listOf(
        MINUS_OPERATOR,
        DIVIDE_OPERATOR,
        PLUS_OPERATOR,
        MULTIPLY_OPERATOR,
        IDENTIFIER,
        LITERAL_NUMBER,
        LITERAL_STRING,
    )

    override fun build(tokens: List<Token>): Expression {
        val result = parseExpression(0, tokens.listIterator())
        return result ?: throw Exception("error while parsing expression")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        //todo, itero 2 veces la lista, feo
        return tokens.all { it.getType() in expectedTypes }
    }

    private fun parseExpression(precedence: Int, tokensToParse : ListIterator<Token>): Expression? {
        var left = parseInitialToken(tokensToParse) ?: return null

        while (tokensToParse.hasNext()) {
            val operator = peek(tokensToParse) ?: break

            val operatorPrecedence = PRECEDENCE[operator.getType()] ?: break
            if (operatorPrecedence < precedence) break

            tokensToParse.next()
            val right = parseExpression(operatorPrecedence + 1, tokensToParse) ?: return null
            left = BinaryExpression(left, operator.getValue(), right)
        }
        return left
    }

    private fun parseInitialToken(tokensToParse: ListIterator<Token>): Expression? {
        val token = peek(tokensToParse)?: return null

        return when (token.getType()) {
            LITERAL_NUMBER -> {
                tokensToParse.next()
                NumberLiteral(token.getValue().toDouble())
            }
            IDENTIFIER -> {
                tokensToParse.next()
                IdentifierExpression(token.getValue())
            }
            MINUS_OPERATOR -> {
                val operator =tokensToParse.next()
                val operand = parseExpression(PRECEDENCE[MINUS_OPERATOR]!! + 1,tokensToParse )
                UnaryExpression(operator.getValue(), operand!!)
            }
            OPENING_PARENS -> {
                tokensToParse.next()
                val expr = parseExpression( 0,tokensToParse )

                val closingParen = peek(tokensToParse)
                if (closingParen == null || closingParen.getType() != CLOSING_PARENS) {
                    throw Exception("Unbalanced parenthesis.")
                }
                expr
            }
            else -> null
        }
    }

    private fun peek(tokensToParse: ListIterator<Token>): Token? {
        return if (tokensToParse.hasNext()) {
            val token = tokensToParse.next()
            tokensToParse.previous() //chiche
            token
        } else {
            null
        }
    }
}