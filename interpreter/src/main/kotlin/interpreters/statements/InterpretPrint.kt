package interpreters.statements

import FunctionCallStatement
import StringValue
import interfaces.*
import interpreters.InterpretExpression
import utils.InterpreterSuccess
import utils.Storage

class InterpretPrint(
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider
) {

    fun interpret(node: FunctionCallStatement, storage: Storage) : InterpreterResult {
        val arguments = node.getArguments()
        var toPrint = emptyList<String>()
        for (argument in arguments) {
            toPrint = listOf(outPutProvider.output(InterpretExpression(
                version,
                outPutProvider,
                inputProvider,
                envProvider).interpret(argument, storage).toString()))
        }
        val result = StringValue(toPrint.joinToString(""))
        return InterpreterSuccess(result)
    }
}