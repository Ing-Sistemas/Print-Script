package interpreters.expressions

import IdentifierExpression
import StoredValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import results.InterpreterFailure
import results.InterpreterSuccess
import utils.Storage

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