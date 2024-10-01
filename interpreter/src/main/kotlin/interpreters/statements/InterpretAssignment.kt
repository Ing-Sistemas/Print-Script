package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.AssignmentStatement
import com.printscript.ast.StoredValue
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterResultInformation
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretAssignment(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: AssignmentStatement, storage: Storage): InterpreterResult {
        val identifier = node.getIdentifier().getIdentifier()
        val value = InterpretExpression(
            outPutProvider,
            inputProvider,
            envProvider,
        ).interpret(node.getValue(), storage)
        val valueToUse = convertToStoredValue(value)
        storage.addToStorage(identifier, valueToUse)
        return InterpreterResultInformation(storage)
    }

    private fun convertToStoredValue(value: InterpreterResult): StoredValue {
        return when (value) {
            is InterpreterSuccess -> {
                value.getSuccess() as StoredValue
            }
            else -> {
                throw IllegalArgumentException("Value is not a stored value")
            }
        }
    }
}