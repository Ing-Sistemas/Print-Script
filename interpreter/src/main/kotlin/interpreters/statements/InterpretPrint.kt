package interpreters.statements

import FunctionCallStatement
import StringValue
import interfaces.*
import interpreters.InterpretExpression
import results.InterpreterSuccess
import utils.Storage

class InterpretPrint(
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: FunctionCallStatement, storage: Storage): InterpreterResult {
        val arguments = node.getArguments()
        val toPrint = mutableListOf<String>()
        for (argument in arguments) {
            val result = InterpretExpression(
                version,
                outPutProvider,
                inputProvider,
                envProvider,
            ).interpret(argument, storage)
            if (result is InterpreterSuccess) {
                toPrint.add(outPutProvider.output(result.getOriginalValue().toString()))
            }
        }
        val result = StringValue(toPrint.joinToString(""))
        return InterpreterSuccess(result)
    }
}