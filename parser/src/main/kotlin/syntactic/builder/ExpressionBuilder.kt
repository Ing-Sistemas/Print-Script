package org.example.parser.syntactic.builder


import BinaryExpression
import BooleanLiteral
import Expression
import IdentifierExpression
import NumberLiteral
import StringLiteral
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
        BOOLEAN,
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
        // weird to use var, but necessary for contemplating cases with binary exp
        var left = parseInitialToken(tokensToParse) ?: return null
        while (tokensToParse.hasNext()) {
            val operator = peek(tokensToParse) ?: break

            val operatorPrecedence = PRECEDENCE[operator.getType()] ?: break
            if (operatorPrecedence <= precedence) break

            tokensToParse.next()
            val right = parseExpression(operatorPrecedence , tokensToParse) ?: return null
            left = BinaryExpression(left, operator.getValue(), right)
        }
        return left
    }

    private fun parseInitialToken(tokensToParse: ListIterator<Token>): Expression? {
        val token = peek(tokensToParse)?: return null

        return when (token.getType()) {
            BOOLEAN -> {
                tokensToParse.next()
                BooleanLiteral(token.getValue().toBoolean())
            }
            LITERAL_STRING -> {
                tokensToParse.next()
                StringLiteral(token.getValue())
            }
            LITERAL_NUMBER -> {
                tokensToParse.next()//moves the iterator pointer
                NumberLiteral(token.getValue().toDouble())
            }
            IDENTIFIER -> {
                tokensToParse.next()//same this as previous man
                IdentifierExpression(token.getValue())
            }
            MINUS_OPERATOR -> {
                val operator = tokensToParse.next() //grabs the operator
                val operand = parseExpression(PRECEDENCE[MINUS_OPERATOR]!! + 1,tokensToParse )//grabs operand
                UnaryExpression(operator.getValue(), operand!!)
                //operator operand example -> - x
            }
            OPENING_PARENS -> {
                tokensToParse.next()// consumes the opening parenthesis
                val expr = parseExpression( 0,tokensToParse )//build the expression inside parens

                val closingParen = tokensToParse.next() //ensure that the parens are closed
                if (closingParen.getType() != CLOSING_PARENS) {
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