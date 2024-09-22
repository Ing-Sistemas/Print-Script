package interpreters.statements

import FunctionCallStatement
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import results.InterpreterFailure
import utils.Storage

class InterpretFunctionCall(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: FunctionCallStatement, storage: Storage): InterpreterResult {
        return when (val functionName = node.getFunctionName()) {
            "println" -> {
                InterpretPrint(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            else -> {
                InterpreterFailure("Function $functionName is not defined")
            }
        }
    }
}