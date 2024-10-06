package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.FunctionCallStatement
import com.printscript.ast.StringValue
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretFunctionCall(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: FunctionCallStatement, storage: Storage): InterpreterResult {
        return when (val functionName = node.getFunctionName()) {
            "println" -> {
                InterpretPrint(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            else -> {
                InterpreterFailure("Function $functionName is not defined")
            }
        }
    }
}