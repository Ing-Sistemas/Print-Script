package org.example.parser.semantic

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
        val leftValue = getIntValue(left.getValue().toString(), storageManager.getStorage())
        val rightValue = getIntValue(right.getValue().toString(), storageManager.getStorage())
        return if (left.getType() == right.getType()) {
            val result = operation(leftValue!!.toInt(), rightValue!!.toInt())
            resultFactory.create(result.toString(), "LITERAL_NUMBER")
        } else {
            resultFactory.createError("Type mismatch for operation")
        }
    }
}