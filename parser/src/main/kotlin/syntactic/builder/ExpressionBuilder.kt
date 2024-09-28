package com.printscript.parser.syntactic.builder

import com.printscript.ast.*
import com.printscript.token.Token
import com.printscript.token.TokenType.*

class ExpressionBuilder : ASTBuilderStrategy {

    // importancia para dar prioridad al cada opp
    private val precedence = mapOf(
        PLUS_OPERATOR to 1,
        MINUS_OPERATOR to 1,
        MULTIPLY_OPERATOR to 2,
        DIVIDE_OPERATOR to 2,
    )

    private val expectedTypes = listOf(
        MINUS_OPERATOR,
        DIVIDE_OPERATOR,
        PLUS_OPERATOR,
        MULTIPLY_OPERATOR,
        IDENTIFIER,
        LITERAL_NUMBER,
        LITERAL_STRING,
        BOOLEAN,
        OPENING_PARENS,
        CLOSING_PARENS,
    )

    override fun build(tokens: List<Token>): Expression {
        val result = parseExpression(0, tokens.listIterator())
        return result ?: throw Exception("error while parsing expression")
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens.all { it.getType() in expectedTypes }
    }

    private fun parseExpression(precedence: Int, tokensToParse: ListIterator<Token>): Expression? {
        var left = parseInitialToken(tokensToParse) ?: return null
        while (tokensToParse.hasNext()) {
            val operator = peek(tokensToParse) ?: break

            val operatorPrecedence = this.precedence[operator.getType()] ?: break
            if (operatorPrecedence <= precedence) break

            tokensToParse.next()
            val right = parseExpression(operatorPrecedence, tokensToParse) ?: return null
            left = BinaryExpression(left, operator.getValue(), right, operator.getPosition())
        }
        return left
    }

    private fun parseInitialToken(tokensToParse: ListIterator<Token>): Expression? {
        val token = peek(tokensToParse) ?: return null

        return when (token.getType()) {
            BOOLEAN -> handleLeaf(token, tokensToParse)
            LITERAL_STRING -> handleLeaf(token, tokensToParse)
            LITERAL_NUMBER -> handleLeaf(token, tokensToParse)
            IDENTIFIER -> handleLeaf(token, tokensToParse)
            MINUS_OPERATOR -> handleUnary(token, tokensToParse)
            OPENING_PARENS -> handleParensExpression(token, tokensToParse)
            else -> null
        }
    }

    private fun handleLeaf(token: Token, tokensToParse: ListIterator<Token>): Expression {
        return when (token.getType()) {
            LITERAL_STRING -> {
                tokensToParse.next()
                StringLiteral(token.getValue(), token.getPosition())
            }
            LITERAL_NUMBER -> {
                tokensToParse.next()
                NumberLiteral(token.getValue().toDouble(), token.getPosition())
            }
            BOOLEAN -> {
                tokensToParse.next()
                BooleanLiteral(token.getValue().toBoolean(), token.getPosition())
            }
            IDENTIFIER -> {
                tokensToParse.next() // same this as previous man
                IdentifierExpression(token.getValue(), token.getPosition())
            }
            else -> { throw Exception("Unexpected leaf token.") }
        }
    }

    private fun handleUnary(token: Token, tokensToParse: ListIterator<Token>): Expression {
        val operator = tokensToParse.next() // grabs the operator
        val operand = parseExpression(precedence[MINUS_OPERATOR]!! + 1, tokensToParse) // grabs operand
        return UnaryExpression(operator.getValue(), operand!!, operator.getPosition())
        // operator operand example -> - x
    }

    private fun handleParensExpression(token: Token, tokensToParse: ListIterator<Token>): Expression? {
        tokensToParse.next() // consumes the opening parenthesis
        val expr = parseExpression(0, tokensToParse) // build the expression inside parens

        val closingParen = tokensToParse.next() // ensure that the parens are closed
        if (closingParen.getType() != CLOSING_PARENS) {
            throw Exception("Unbalanced parenthesis.")
        }
        return expr
    }

    private fun peek(tokensToParse: ListIterator<Token>): Token? {
        return if (tokensToParse.hasNext()) {
            val token = tokensToParse.next()
            if (!expectedTypes.contains(token.getType())) throw Exception("Unexpected token ${token.getType()}")
            tokensToParse.previous() // chiche
            token
        } else {
            null
        }
    }
}