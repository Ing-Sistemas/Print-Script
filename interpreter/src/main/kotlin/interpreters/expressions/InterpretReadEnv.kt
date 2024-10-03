package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.BooleanValue
import com.printscript.ast.NumberValue
import com.printscript.ast.ReadEnvNode
import com.printscript.ast.StringValue
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretReadEnv(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ReadEnvNode, storage: Storage): InterpreterResult {
        val envVarName = node.getEnvVar()
        val envValue = envProvider.getEnv(envVarName)
            ?: return InterpreterFailure("Environment variable $envVarName not found")

        return try {
            if (envValue == "true" || envValue == "false") {
                InterpreterSuccess(BooleanValue(envValue.toBoolean()))
            } else if (envValue.toDoubleOrNull() != null) {
                InterpreterSuccess(NumberValue(envValue.toDouble()))
            } else {
                InterpreterSuccess(StringValue(envValue))
            }
        } catch (e: Exception) {
            InterpreterFailure("Error parsing environment variable $envVarName")
        }
    }
}