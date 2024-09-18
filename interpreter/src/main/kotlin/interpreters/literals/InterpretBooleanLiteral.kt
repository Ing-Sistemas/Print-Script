package interpreters.literals

import BooleanLiteral
import BooleanValue
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import utils.InterpreterResultInformation
import utils.InterpreterSuccess
import utils.Storage

class InterpretBooleanLiteral (private val outPutProvider: OutPutProvider) {

    fun interpret(node: BooleanLiteral, storage: Storage) : InterpreterResult{
        val value = BooleanValue(node.getValue())
        return InterpreterSuccess(value)
    }
}