package com.printscript.interpreter.interpreters.literals

import com.printscript.ast.NumberLiteral
import com.printscript.ast.NumberValue
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

class InterpretNumberLiteral(private val outPutProvider: OutPutProvider) {

    fun interpret(node: NumberLiteral, storage: Storage): InterpreterResult {
        val value = NumberValue(if (node.getValue() % 1 == 0.0) node.getValue().toInt() else node.getValue())
        return InterpreterSuccess(value)
    }
}