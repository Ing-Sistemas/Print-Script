package com.printscript.interpreter.interpreters

import com.printscript.*
import com.printscript.ast.*
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.interpreters.expressions.InterpretBinaryExpression
import com.printscript.interpreter.interpreters.expressions.InterpretIdentifier
import com.printscript.interpreter.interpreters.expressions.InterpretUnaryExpression
import com.printscript.interpreter.results.*
import com.printscript.interpreter.utils.Storage

class InterpretExpression(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: Expression, storage: Storage): InterpreterResult {
        return when (node) {
            is BinaryExpression -> {
                InterpretBinaryExpression(outPutProvider, inputProvider, envProvider).interpret(node, storage)
            }
            is UnaryExpression -> {
                InterpretUnaryExpression(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is IdentifierExpression -> {
                InterpretIdentifier(outPutProvider).interpret(node, storage)
            }
            is TypeDeclarationExpression -> {
                val nodeTypeString = StringValue(outPutProvider.output(node.getType()))
                return InterpreterSuccess(nodeTypeString)
            }
            is BooleanLiteral -> {
                InterpretLiteral(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is NumberLiteral -> {
                InterpretLiteral(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is StringLiteral -> {
                InterpretLiteral(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is ReadEnvNode -> {
                InterpretReadEnv(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is ReadInputNode -> {
                InterpretReadInput(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
        }
    }
}