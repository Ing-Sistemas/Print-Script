package org.example.parser.semantic

import BinaryExpression
import Visitor
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation
import org.example.parser.semantic.result.ResultNumber

class OperationCheck(
    private val resultFactory: ResultFactory,
) {

    fun checkBinaryOperation(
        node: BinaryExpression,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val left = node.getLeft().accept(visitor)
        val right = node.getRight().accept(visitor)
        return performOperation(left, right, node.getOperator())
    }

    private fun performOperation(
        left: ResultInformation,
        right: ResultInformation,
        operator: String,
    ): ResultInformation {
        return when (operator) {
            "+" -> {
                if (left.getType() == DataType.STRING || right.getType() == DataType.STRING) {
                    resultFactory.createStringResult(left.getValue<String>() + right.getValue<String>(), DataType.STRING)
                } else {
                    resultFactory.createNumberResult(left.getValue<Double>() + right.getValue<Double>(), DataType.NUMBER)
                }
            }
            "-" -> handleOperation(left as ResultNumber, right as ResultNumber, Double::minus)
            "/" -> handleOperation(left as ResultNumber, right as ResultNumber, Double::div)
            "*" -> handleOperation(left as ResultNumber, right as ResultNumber, Double::times)
            else -> resultFactory.createError("Invalid operator specified")
        }
    }

    private fun handleOperation(
        left: ResultInformation,
        right: ResultInformation,
        operation: (Double, Double) -> Double,
    ): ResultInformation {
        val leftValue = left.getValue<Double>()
        val rightValue = right.getValue<Double>()
        return if (left.getType() == right.getType()) {
            val result = operation(leftValue, rightValue)
            resultFactory.create(result, left.getType())
        } else {
            resultFactory.createError("Type mismatch for operation")
        }
    }
}