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
    private val version: String,
) {

    fun interpret(node: FunctionCallStatement, storage: Storage): InterpreterResult {
        val functionName = node.getFunctionName()

        when (version) {
            "1.0" -> {
                if (node.getFunctionName() != "println") {
                    return InterpreterFailure("Function $functionName is not defined in 1.0")
                }
                return InterpretPrint(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            "1.1" -> {
                if (node.getFunctionName() == "println") {
                    return InterpretPrint(
                        version,
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage)
                }
                if (node.getFunctionName() == "readInput") {
                    TODO("readInput")
                } else {
                    return InterpreterFailure("Function $functionName is not defined in 1.1")
                }
            }
            else -> {
                return InterpreterFailure("This function call (${node.getFunctionName()}) is not supported in this version: $version")
            }
        }
    }
}