package interpreters.expressions

import BinaryExpression
import BooleanValue
import NumberValue
import StringValue
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import results.InterpreterFailure
import results.InterpreterSuccess
import utils.Storage

class InterpretBinaryExpression(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: BinaryExpression, storage: Storage): InterpreterResult {
        val operator = node.getOperator()
        val nodeLeftResult = InterpretExpression(outPutProvider, inputProvider, envProvider).interpret(node.getLeft(), storage)
        val nodeRightResult = InterpretExpression(outPutProvider, inputProvider, envProvider).interpret(node.getRight(), storage)
        val leftNodeConverted = nodeLeftResult as InterpreterSuccess
        val leftNode = leftNodeConverted.getOriginalValue()
        val rightNodeConverted = nodeRightResult as InterpreterSuccess
        val rightNode = rightNodeConverted.getOriginalValue()
        if (leftNode is BinaryExpression) {
            interpret(leftNode, storage)
        }
        if (rightNode is BinaryExpression) {
            interpret(rightNode, storage)
        }
        return when {
            leftNode is Double && rightNode is Double -> {
                InterpreterSuccess(NumberValue(applyOperator(leftNode, operator, rightNode)))
            }

            leftNode is String && rightNode is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode + rightNode))
            }

            leftNode is Double && rightNode is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode.toString() + rightNode))
            }

            leftNode is String && rightNode is Double && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNode + rightNode.toString()))
            }

            else -> {
                return InterpreterFailure("Invalid operands for operator: $operator")
            }
        }
    }

    private fun changeValue(result: InterpreterSuccess): Any {
        return when (result.getSuccess()) {
            is BooleanValue -> result.getSuccess().toString()
            is NumberValue -> result.getSuccess().toString().toDouble()
            is StringValue -> result.getSuccess().toString()
            else -> {
                result
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