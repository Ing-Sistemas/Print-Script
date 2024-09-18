package interpreters.expressions

import IdentifierExpression
import StringValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import utils.InterpreterSuccess
import utils.Storage

class InterpretIdentifier (
    private val outPutProvider: OutPutProvider
) {

    fun interpret(node: IdentifierExpression, storage: Storage) : InterpreterResult {
        val value = StringValue(outPutProvider.output(storage.getFromStorage(node.getIdentifier()).toString()))
        return InterpreterSuccess(value)
    }
}