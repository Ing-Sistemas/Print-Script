package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.FunctionCallStatement
import com.printscript.ast.StringValue
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.InterpretExpression
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretPrint(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: FunctionCallStatement, storage: Storage): InterpreterResult {
        val arguments = node.getArguments()
        val toPrint = mutableListOf<String>()
        for (argument in arguments) {
            val result = InterpretExpression(
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