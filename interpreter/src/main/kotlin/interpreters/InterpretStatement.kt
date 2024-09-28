package com.printscript.interpreter.interpreters

import com.printscript.ast.*
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.statements.*
import com.printscript.interpreter.utils.Storage

class InterpretStatement(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: Statement, storage: Storage): InterpreterResult {
        return when (node) {
            is VariableDeclarationStatement -> {
                InterpretVariableDeclaration(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }

            is FunctionCallStatement -> {
                InterpretFunctionCall(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }

            is AssignmentStatement -> {
                InterpretAssignment(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }

            is EmptyVarDeclarationStatement -> {
                InterpretEmptyVariable(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }

            is IfStatement -> {
                InterpretIf(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
        }
    }
}