package com.printscript.interpreter.interpreters.literals

import com.printscript.ast.StringLiteral
import com.printscript.ast.StringValue
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretStringLiteral(private val outPutProvider: OutPutProvider) {

    fun interpret(node: StringLiteral, storage: Storage): InterpreterResult {
        val value = StringValue(node.getValue())
        return InterpreterSuccess(value)
    }
}