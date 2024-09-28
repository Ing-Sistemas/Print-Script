package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.BooleanValue
import com.printscript.ast.IfStatement
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretIf(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: IfStatement, storage: Storage): InterpreterResult {
        val ifBody = node.getThenStatement()
        val elseBody = node.getElseStatement()
        val condition = InterpretExpression(
            outPutProvider,
            inputProvider,
            envProvider,
        ).interpret(node.getCondition(), storage)

        if (condition is InterpreterSuccess && condition.getSuccess() is BooleanValue) {
            if (getConditionValue(condition.getSuccess() as BooleanValue)) {
                ifBody.forEach { astNode ->
                    return Interpreter(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(astNode, storage)
                }
            } else {
                elseBody?.forEach { astNode ->
                    return Interpreter(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(astNode, storage)
                }
            }
        } else {
            return InterpreterFailure("If condition must be a boolean literal")
        }
        return InterpreterFailure("If condition must be a boolean literal")
    }
    private fun getConditionValue(node: BooleanValue): Boolean {
        return node.value
    }
}