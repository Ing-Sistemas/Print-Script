package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.*
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretReadInput(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val expectedType: String,
) {
    fun interpret(node: ReadInputNode, storage: Storage): InterpreterResult {
        val prompt = node.getPrompt()

        outPutProvider.output(prompt)

        val input = inputProvider.readInput(prompt)
        // val input = "Felipe"

        if (input == "true" || input == "false") {
            if (expectedType == "boolean") {
                storage.addToStorage(input, BooleanValue(input.toBoolean()))
                return InterpreterSuccess(BooleanValue(input.toBoolean()))
            }
        } else if (input != null) {
            if (input.toDoubleOrNull() != null) {
                if (expectedType == "number") {
                    storage.addToStorage(input, NumberValue(input.toDouble()))
                    return InterpreterSuccess(NumberValue(input.toDouble()))
                }
            } else if (expectedType == "string") {
                storage.addToStorage(input, StringValue(input))
                return InterpreterSuccess(StringValue(input))
            } else {
                return InterpreterFailure("Not valid input 1")
            }
        } else {
            return InterpreterFailure("Not valid input 2")
        }
        return InterpreterFailure("Not valid input 3")
    }
}