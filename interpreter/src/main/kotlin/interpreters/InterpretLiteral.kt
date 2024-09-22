package interpreters

import BooleanLiteral
import Literal
import NumberLiteral
import StringLiteral
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.literals.InterpretBooleanLiteral
import interpreters.literals.InterpretNumberLiteral
import interpreters.literals.InterpretStringLiteral
import results.InterpreterFailure
import utils.Storage

class InterpretLiteral(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: Literal, storage: Storage): InterpreterResult {
        return when (node) {
            is NumberLiteral -> {
                InterpretNumberLiteral(outPutProvider).interpret(node, storage)
            }
            is StringLiteral -> {
                InterpretStringLiteral(outPutProvider).interpret(node, storage)
            }
            is BooleanLiteral -> {
                InterpretBooleanLiteral(outPutProvider).interpret(node, storage)
            }
        }
    }
}