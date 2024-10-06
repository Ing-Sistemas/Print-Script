package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.BinaryExpression
import com.printscript.ast.NumberValue
import com.printscript.ast.StringValue
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretBinaryExpression(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: BinaryExpression, storage: Storage): InterpreterResult {
        val operator = node.getOperator()
        val nodeLeftResult = InterpretExpression(outPutProvider, inputProvider, envProvider).interpret(node.getLeft(), storage)
        val nodeRightResult = InterpretExpression(outPutProvider, inputProvider, envProvider).interpret(node.getRight(), storage)

        if (nodeLeftResult !is InterpreterSuccess || nodeRightResult !is InterpreterSuccess) {
            return InterpreterFailure("Invalid left or right expression")
        }

        val leftNode = nodeLeftResult.getIntValue()
        val rightNode = nodeRightResult.getIntValue()

        val leftValue = when (leftNode) {
            is NumberValue -> nodeLeftResult.getIntValue()
            else -> leftNode
        }

        val rightValue = when (rightNode) {
            is NumberValue -> nodeRightResult.getIntValue()
            else -> rightNode
        }

        return when {
            leftValue is Int && rightValue is Int -> {
                InterpreterSuccess(NumberValue(applyOperator(leftValue.toDouble(), operator, rightValue.toDouble())))
            }
            leftValue is Double && rightValue is Double -> {
                InterpreterSuccess(NumberValue(applyOperator(leftValue, operator, rightValue)))
            }
            leftNode is String && rightNode is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode + rightNode))
            }
            leftValue is Double && rightNode is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftValue.toString() + rightNode))
            }
            leftNode is String && rightValue is Double && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode + rightValue.toString()))
            }
            leftValue is Int && rightNode is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftValue.toString() + rightNode))
            }
            leftNode is String && rightValue is Int && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode + rightValue.toString()))
            }
            leftValue is Double && rightValue is Int -> {
                InterpreterSuccess(NumberValue(applyOperator(leftValue, operator, rightValue.toDouble())))
            }
            leftValue is Int && rightValue is Double -> {
                InterpreterSuccess(NumberValue(applyOperator(leftValue.toDouble(), operator, rightValue)))
            }
            else -> {
                InterpreterFailure("Invalid operands for operator: $operator")
            }
        }
    }

    private fun applyOperator(left: Double, operator: String, right: Double): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}