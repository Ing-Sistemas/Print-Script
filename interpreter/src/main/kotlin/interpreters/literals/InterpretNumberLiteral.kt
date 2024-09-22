package interpreters.literals

import NumberLiteral
import NumberValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import results.InterpreterSuccess
import utils.Storage

class InterpretNumberLiteral(private val outPutProvider: OutPutProvider) {

    fun interpret(node: NumberLiteral, storage: Storage): InterpreterResult {
        val value = NumberValue(node.getValue())
        return InterpreterSuccess(value)
    }
}