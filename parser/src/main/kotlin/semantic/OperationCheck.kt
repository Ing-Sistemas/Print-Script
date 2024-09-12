package org.example.semantic

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

    private fun handleOperation(
        left: ResultInformation,
        right: ResultInformation,
        operation: (Int, Int) -> Int,
    ): ResultInformation {
        val leftValue = getValueFromStorageOrLiteral(left.getValue())
        val rightValue = getValueFromStorageOrLiteral(right.getValue())
        return if (leftValue != null && rightValue != null) {
            val result = operation(leftValue, rightValue)
            resultFactory.create(result.toString(), "LITERAL_NUMBER")
        } else {
            resultFactory.createError("Invalid integer values for operation")
        }
    }

    private fun getValueFromStorageOrLiteral(value: String?): Int? {
        return when {
            value == null -> null
            value.toIntOrNull() != null -> value.toInt()
            else -> {
                val storedValue = storageManager.get(value)
                when (storedValue) {
                    is Int -> storedValue
                    is String -> storedValue.toIntOrNull()
                    else -> null
                }
            }
        }
    }
}