package interpreters.statements

import BooleanValue
import IfStatement
import Interpreter
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import results.InterpreterFailure
import results.InterpreterSuccess
import utils.Storage

class InterpretIf(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: IfStatement, storage: Storage): InterpreterResult {
        val ifBody = node.getThenStatement()
        val elseBody = node.getElseStatement()
        val condition = InterpretExpression(
            outPutProvider,
            inputProvider,
            envProvider,
        ).interpret(node.getCondition(), storage)

        if (condition is InterpreterSuccess && condition.getSuccess() is BooleanValue) {
            if (getConditionValue(condition.getSuccess() as BooleanValue)) {
                ifBody.forEach { astNode ->
                    return Interpreter(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(astNode, storage)
                }
            } else {
                elseBody?.forEach { astNode ->
                    return Interpreter(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(astNode, storage)
                }
            }
        } else {
            return InterpreterFailure("If condition must be a boolean literal")
        }
        return InterpreterFailure("If condition must be a boolean literal")
    }
    private fun getConditionValue(node: BooleanValue): Boolean {
        return node.value
    }
}