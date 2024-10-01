package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.BinaryExpression
import com.printscript.ast.BooleanValue
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