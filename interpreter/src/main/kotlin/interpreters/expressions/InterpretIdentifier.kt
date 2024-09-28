package com.printscript.interpreter.interpreters.expressions

import com.printscript.ast.IdentifierExpression
import com.printscript.ast.StoredValue
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretIdentifier(
    private val outPutProvider: OutPutProvider,
) {

    fun interpret(node: IdentifierExpression, storage: Storage): InterpreterResult {
        val value = storage.getFromStorage(node.getIdentifier())
        return if (value is StoredValue) {
            InterpreterSuccess(value)
        } else {
            InterpreterFailure("Identifier not found")
        }
    }
}