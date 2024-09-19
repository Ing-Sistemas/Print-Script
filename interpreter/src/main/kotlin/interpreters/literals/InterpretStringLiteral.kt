package interpreters.literals

import StringLiteral
import StringValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import results.InterpreterSuccess
import utils.Storage

class InterpretStringLiteral(private val outPutProvider: OutPutProvider) {

    fun interpret(node: StringLiteral, storage: Storage): InterpreterResult {
        val value = StringValue(node.getValue())
        return InterpreterSuccess(value)
    }
}