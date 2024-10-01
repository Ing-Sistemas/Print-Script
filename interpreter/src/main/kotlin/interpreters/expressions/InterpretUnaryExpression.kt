package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.*
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretUnaryExpression(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ASTNode, storage: Storage): InterpreterResult {
        val unaryNode = node as UnaryExpression
        val right = unaryNode.getRight()
        val useRight = InterpretExpression(outPutProvider, inputProvider, envProvider).interpret(right, storage)
        if (useRight is InterpreterSuccess && useRight.getSuccess() !is NumberValue) {
            return InterpreterFailure("Unary expression must be a number")
        }
        when (unaryNode.getOperator()) {
            "-" -> {
                if (useRight is InterpreterSuccess) {
                    val numberToUse = (useRight.getOriginalValue()) as Double
                    val negateNumber = -numberToUse
                    return InterpreterSuccess(toCustom(negateNumber))
                } else {
                    return InterpreterFailure("There is an interpreter Failure in: $useRight")
                }
            }
            else -> return InterpreterFailure("Unary operator ${node.getOperator()} not supported")
        }
    }
    private fun toCustom(value: Any?): NumberValue {
        return NumberValue(value as Double)
    }
}