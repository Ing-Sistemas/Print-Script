package com.printscript.parser.syntactic.builder

import com.printscript.ast.*
import com.printscript.parser.syntactic.SyntacticAnalyzer
import com.printscript.parser.syntactic.SyntacticFail
import com.printscript.parser.syntactic.SyntacticResult
import com.printscript.parser.syntactic.SyntacticSuccess
import com.printscript.token.Token
import com.printscript.token.TokenType.*

class IfStatementBuilder : ASTBuilderStrategy {

    override fun build(tokens: List<Token>): SyntacticResult {
        return SyntacticSuccess(parseIfStatement(tokens.listIterator()))
    }

    override fun isValidStruct(tokens: List<Token>): Boolean {
        return tokens[0].getType() == IF &&
            tokens[1].getType() == OPENING_PARENS
    }

    private fun parseIfStatement(tokens: ListIterator<Token>): IfStatement {
        val ifToken = tokens.next()
        val condition = handleCondition(tokens)
        val thenBlock = handleBlock(tokens)
        val elseBlock = if (tokens.hasNext() && tokens.next().getType() == ELSE) {
            handleBlock(tokens)
        } else {
            null
        }
        return IfStatement(condition, thenBlock, elseBlock, ifToken.getPosition())
    }

    private fun handleCondition(tokens: ListIterator<Token>): Expression {
        tokens.next() // consume (
        val conditionToken = tokens.next()
        val closingParen = tokens.next()
        if (closingParen.getType() != CLOSING_PARENS) {
            throw Exception("Expected closing parens")
        }
        return when (conditionToken.getType()) {
            IDENTIFIER -> return IdentifierExpression(conditionToken.getValue(), conditionToken.getPosition())
            BOOLEAN -> BooleanLiteral(conditionToken.getValue().toBoolean(), conditionToken.getPosition())
            else -> throw Exception("Unexpected condition token")
        }
    }

    private fun handleBlock(tokens: ListIterator<Token>): List<ASTNode> {
        val openingCurly = tokens.next()
        if (openingCurly.getType() != OPENING_BRACES) {
            throw Exception("Expected closing curly bracks")
        }
        val blockList = mutableListOf<ASTNode>()
        val blockTokens = mutableListOf<Token>()
        while (tokens.hasNext()) {
            val token = tokens.next()
            if (token.getType() == CLOSING_BRACES) {
                break
            }
            if (token.getType() == SEMICOLON) {
                blockTokens.add(token)
                when (val ast = SyntacticAnalyzer().build(blockTokens.toList().iterator())) { // toList para no perder los tokens
                    is SyntacticSuccess -> blockList.add(ast.astNode)
                    is SyntacticFail -> throw Exception(ast.message)
                }
                blockTokens.clear()
            }
            blockTokens.add(token)
        }
        return blockList
    }
}