package org.example.parser.semantic

import org.example.BinaryNode
import org.example.Visitor

class OperationCheck(
    private val resultFactory: ResultFactory,
    private val storageManager: StorageManager,
) {

    fun checkBinaryOperation(
        node: BinaryNode,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val left = node.getLeft().accept(visitor)
        val right = node.getRight().accept(visitor)
        return performOperation(left, right, node.getValue())
    }

    private fun performOperation(
        left: ResultInformation,
        right: ResultInformation,
        operator: String,
    ): ResultInformation {
        return when (operator) {
            "+" -> resultFactory.create(left.getValue() + right.getValue(), null)
            "-" -> handleOperation(left, right, Int::minus)
            "/" -> handleOperation(left, right, Int::div)
            "*" -> handleOperation(left, right, Int::times)
            else -> resultFactory.createError("Invalid operator specified")
        }
    }

    private fun getIntValue(key: String, map: Map<String, Any>): Int? {
        val value = map[key]
        return when (value) {
            is Int -> value
            is String -> value.toIntOrNull() // string to int
            else -> null // Return null > no int/string
        }
    }

    private fun handleOperation(
        left: ResultInformation,
        right: ResultInformation,
        operation: (Int, Int) -> Int,
    ): ResultInformation {
        val leftValue = left.getValue().toString().toIntOrNull()
        val rightValue = right.getValue().toString().toIntOrNull()
        return if (leftValue != null && rightValue != null) {
            val result = operation(leftValue, rightValue)
            resultFactory.create(result.toString(), "LITERAL_NUMBER")
        } else {
            resultFactory.createError("Invalid integer values for operation")
        }
    }
}