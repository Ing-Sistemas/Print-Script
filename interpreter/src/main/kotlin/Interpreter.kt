package com.printscript.interpreter

import com.printscript.ast.*
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.*
import com.printscript.interpreter.results.InterpreterFailure
import com.printscript.interpreter.utils.Storage

class Interpreter(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ASTNode, storage: Storage): List<String> {
        val result = mutableListOf<String>()
        when (node) {
            is Literal -> {
                if (InterpretLiteral(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage) is InterpreterFailure
                ) {
                    result.add(InterpreterFailure("Failed to interpret literal: $node").getErrorMessage())
                } else {
                    result.addAll(emptyList())
                }
            }
            is Expression -> {
                if (InterpretExpression(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage) is InterpreterFailure
                ) {
                    result.add(InterpreterFailure("Failed to interpret expression: $node").getErrorMessage())
                } else {
                    result.addAll(emptyList())
                }
            }
            is Statement -> {
                if (InterpretStatement(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage) is InterpreterFailure
                ) {
                    result.add(InterpreterFailure("Failed to interpret statement: $node").getErrorMessage())
                } else {
                    result.addAll(emptyList())
                }
            }
            is ReadEnvNode -> {
                if (InterpretReadEnv(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage) is InterpreterFailure
                ) {
                    result.add(InterpreterFailure("Failed to interpret readEnv: ${node.getIdentifierName()}").getErrorMessage())
                } else {
                    result.addAll(emptyList())
                }
            }
            is ReadInputNode -> {
                if (InterpretReadInput(
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage) is InterpreterFailure
                ) {
                    result.add(InterpreterFailure("Failed to interpret readInput: ${node.getInput()}").getErrorMessage())
                } else {
                    result.addAll(emptyList())
                }
            }
        }
        return result
    }
}