package interpreters

import BinaryExpression
import BooleanLiteral
import Expression
import IdentifierExpression
import NumberLiteral
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import utils.Storage

class InterpretExpression {

    fun interpret(node: Expression, storage: Storage): Any {
        return evaluateNode(node, storage)
    }

    private fun evaluateNode(node: Expression, storage: Storage): Any {
        return when (node) {
            is NumberLiteral -> InterpretLiteral().interpret(node, storage)
            is StringLiteral -> InterpretLiteral().interpret(node, storage)
            is BooleanLiteral -> InterpretLiteral().interpret(node, storage)
            is BinaryExpression -> {
                val left = evaluateNode(node.getLeft(), storage)
                val right = evaluateNode(node.getRight(), storage)
                val operator = node.getOperator()

                when {
                    left is Double && right is Double ->
                        { applyOperator(left, operator, right) }

                    left is String && right is String && operator == "+" ->
                        { left + right }

                    left is Double && right is String && operator == "+" ->
                        { left.toString() + right }

                    left is String && right is Double && operator == "+" ->
                        { left + right.toString() }

                    else -> { throw IllegalArgumentException("Invalid operands for operator: $operator") }
                }
            }
            is UnaryExpression -> {
                val right = node.getRight()
                val useRight = interpret(right, storage)
                when (node.getOperator()) {
                    "-" -> -(useRight as Double)
                    else -> throw IllegalArgumentException("Invalid operator")
                }
            }
            is IdentifierExpression -> {
                storage.getFromStorage(node.getIdentifier())!!
            }
            is TypeDeclarationExpression -> {
                return node.getType()
            }
        }
    }

    private fun applyOperator(left: Double, operator: String, right: Double): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> { throw IllegalArgumentException() }
        }
    }
}