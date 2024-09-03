package org.example.visitor

import BinaryExpression
import Expression
import IdentifierExpression
import Literal
import NumberLiteral
import Statement
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import Visitor

class InterpreterVisitor : Visitor<Any> {
    val storage = mutableMapOf<String, Any>()

    override fun visit(expression: Expression): Any {
        return evaluateNode(expression)
    }

    private fun evaluateNode(node: Any): Any {
        return when (node) {
            is BinaryExpression -> {
                val left = evaluateNode(node.getLeft().accept(this))
                val right = evaluateNode(node.getRight().accept(this))
                val operator = node.getOperator()

                when {
                    left is Int && right is Int ->
                        { applyOperator(left, operator, right) }

                    left is String && right is String && operator == "+" ->
                        { left + right }

                    left is Int && right is String && operator == "+" ->
                        { left.toString() + right }

                    left is String && right is Int && operator == "+" ->
                        { left + right.toString() }

                    else -> { throw IllegalArgumentException("Invalid operands for operator: $operator") }
                }
            }
            is UnaryExpression -> {
                val right = node.getRight().accept(this)
                when (node.getOperator()) {
                    "-" -> -(right as Int)
                    else -> throw IllegalArgumentException("Invalid operator")
                }
            }
            is IdentifierExpression -> {
                return storage[node.getIdentifier()]!!
            }
            is TypeDeclarationExpression -> {
                return node.getType()
            }
            is NumberLiteral -> visit(node)
            is StringLiteral -> visit(node)
            else -> {}
        }
    }

    private fun applyOperator(left: Int, operator: String, right: Int): Int {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> { throw IllegalArgumentException() }
        }
    }

    override fun visit(literal: Literal): Any {
        return when (literal) {
            is NumberLiteral -> literal.getValue()
            is StringLiteral -> literal.getValue()
            else -> {}
        }
    }

    override fun visit(statement: Statement): Any {
        TODO("Not yet implemented")
    }
}