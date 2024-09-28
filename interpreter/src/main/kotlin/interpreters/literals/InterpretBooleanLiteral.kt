package com.printscript.interpreter.interpreters.literals

import com.printscript.ast.BooleanLiteral
import com.printscript.ast.BooleanValue
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretBooleanLiteral(private val outPutProvider: OutPutProvider) {

    fun interpret(node: BooleanLiteral, storage: Storage): InterpreterResult {
        val value = BooleanValue(node.getValue())
        return InterpreterSuccess(value)
    }
}