package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.StoredValue
import com.printscript.ast.VariableDeclarationStatement
import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterResultInformation
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretVariableDeclaration(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: VariableDeclarationStatement, storage: Storage): InterpreterResult {
        val declarator = node.getAssignmentExpression()
        val identifier = Interpreter(outPutProvider, inputProvider, envProvider).interpret(declarator, storage)
        val value = node.getAssignmentExpression().getValue()
        val storedValue = convertToStoredValue(
            InterpretExpression(
                outPutProvider,
                inputProvider,
                envProvider,
            ).interpret(value, storage),
        )
        storage.addToStorage(identifier.toString(), storedValue)
        return InterpreterResultInformation(storage)
    }
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