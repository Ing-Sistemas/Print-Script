package com.printscript.interpreter.interpreters

import com.printscript.ast.BooleanLiteral
import com.printscript.ast.Literal
import com.printscript.ast.NumberLiteral
import com.printscript.ast.StringLiteral
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.literals.InterpretBooleanLiteral
import com.printscript.interpreter.interpreters.literals.InterpretNumberLiteral
import com.printscript.interpreter.interpreters.literals.InterpretStringLiteral
import com.printscript.interpreter.utils.Storage

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