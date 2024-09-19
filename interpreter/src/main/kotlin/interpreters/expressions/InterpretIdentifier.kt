package interpreters.expressions

import BooleanValue
import IdentifierExpression
import NumberValue
import StoredValue
import StringValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import utils.InterpreterFailure
import utils.InterpreterSuccess
import utils.Storage

class InterpretIdentifier (
    private val outPutProvider: OutPutProvider
) {

    fun interpret(node: IdentifierExpression, storage: Storage) : InterpreterResult {
        val value = storage.getFromStorage(node.getIdentifier())
        return if (value is StoredValue) {
            InterpreterSuccess(value)
        } else {
            InterpreterFailure("Identifier not found")
        }

    }
}