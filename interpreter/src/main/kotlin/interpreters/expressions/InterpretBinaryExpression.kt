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

        val leftValue = nodeLeftResult.getSuccess()
        val rightValue = nodeRightResult.getSuccess()

        return when {
            leftValue is NumberValue && rightValue is NumberValue -> {
                InterpreterSuccess(applyOperator(leftValue, operator, rightValue))
            }
            leftValue is StringValue && rightValue is StringValue && operator == "+" -> {
                InterpreterSuccess(StringValue(leftValue.value + rightValue.value))
            }
            leftValue is StringValue && rightValue is NumberValue && operator == "+" -> {
                InterpreterSuccess(StringValue(leftValue.value + rightValue.value))
            }
            leftValue is NumberValue && rightValue is StringValue && operator == "+" -> {
                InterpreterSuccess(StringValue(leftValue.value.toString() + rightValue.value))
            }
            else -> {
                InterpreterFailure("Invalid operands for operator: $operator")
            }
        }
    }

    private fun applyOperator(left: NumberValue, operator: String, right: NumberValue): NumberValue {
        return when (operator) {
            "+" -> left.plus(right)
            "-" -> left.minus(right)
            "*" -> left.times(right)
            "/" -> left.div(right)
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}