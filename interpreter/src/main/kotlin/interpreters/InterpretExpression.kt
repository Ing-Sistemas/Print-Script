package interpreters

import BinaryExpression
import BooleanLiteral
import Expression
import IdentifierExpression
import NumberLiteral
import StringLiteral
import StringValue
import TypeDeclarationExpression
import UnaryExpression
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.expressions.InterpretBinaryExpression
import interpreters.expressions.InterpretIdentifier
import interpreters.expressions.InterpretUnaryExpression
import results.InterpreterSuccess
import utils.Storage

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
        }
    }
}