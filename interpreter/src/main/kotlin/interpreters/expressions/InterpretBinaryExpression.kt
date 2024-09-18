package interpreters.expressions

import BinaryExpression
import BooleanLiteral
import BooleanValue
import Expression
import IdentifierExpression
import Interpreter
import NumberLiteral
import NumberValue
import StringLiteral
import StringValue
import TypeDeclarationExpression
import UnaryExpression
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import interpreters.InterpretLiteral
import utils.InterpreterFailure
import utils.InterpreterSuccess
import utils.Storage

class InterpretBinaryExpression (
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider
){

    fun interpret (node: BinaryExpression, storage: Storage): InterpreterResult {
        val operator = node.getOperator()
        val nodeLeftResult = InterpretExpression(version, outPutProvider, inputProvider, envProvider).interpret(node.getLeft(), storage)
        val nodeRightResult = InterpretExpression(version, outPutProvider, inputProvider, envProvider).interpret(node.getRight(), storage)
        val leftNodeConverted = changeValue(nodeLeftResult as InterpreterSuccess)
        val rightNodeConverted = changeValue(nodeRightResult as InterpreterSuccess)
        if (leftNodeConverted is BinaryExpression) {
            interpret(leftNodeConverted, storage)
        }
        if (rightNodeConverted is BinaryExpression) {
            interpret(rightNodeConverted, storage)
        }
        return when {
            leftNodeConverted is Double && rightNodeConverted is Double -> {
                InterpreterSuccess(NumberValue(applyOperator(leftNodeConverted, operator, rightNodeConverted)))
            }

            leftNodeConverted is String && rightNodeConverted is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNodeConverted + rightNodeConverted))
            }

            leftNodeConverted is Double && rightNodeConverted is String && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNodeConverted.toString() + rightNodeConverted))
            }

            leftNodeConverted is String && rightNodeConverted is Double && operator == "+" -> {
                InterpreterSuccess(StringValue(leftNodeConverted + rightNodeConverted.toString()))
            }

            else -> {
                return InterpreterFailure("Invalid operands for operator: $operator")
            }
        }
    }

    private fun changeValue (result: InterpreterSuccess) : Any {
        return when (result.getSuccess()) {
            is BooleanValue -> result.getSuccess().toString()
            is NumberValue -> result.getSuccess().toString().toDouble()
            is StringValue -> result.getSuccess().toString()
            else -> {
                result
            }
        }
    }

    private fun applyOperator(left: Double, operator: String, right: Double): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> { throw IllegalArgumentException() }
        }
    }

//    private fun evaluateNode(node: Expression, storage: Storage, version: String): Any {
//        return when (node) {
//            is BinaryExpression -> {
//                val left = evaluateNode(node.getLeft(), storage, version)
//                val right = evaluateNode(node.getRight(), storage, version)
//                val operator = node.getOperator()
//
//                when {
//                    left is Double && right is Double ->
//                    { applyOperator(left, operator, right)}
//                    left is String && right is String && operator == "+" ->
//                    { left + right }
//
//                    left is Double && right is String && operator == "+" ->
//                    { left.toString() + right }
//
//                    left is String && right is Double && operator == "+" ->
//                    { left + right.toString() }
//
//                    else -> { throw IllegalArgumentException("Invalid operands for operator: $operator") }
//                }
//            }
//            is IdentifierExpression -> {
//                InterpretIdentifier(outPutProvider).interpret(node, storage)
//            }
//            is BooleanLiteral -> {
//                InterpretLiteral(version, outPutProvider, inputProvider, envProvider).interpret(node, storage)
//            }
//            is NumberLiteral -> {
//                InterpretLiteral(version, outPutProvider, inputProvider, envProvider).interpret(node, storage)
//            }
//            is StringLiteral -> {
//                InterpretLiteral(version, outPutProvider, inputProvider, envProvider).interpret(node, storage)
//            }
//            is TypeDeclarationExpression -> {
//                outPutProvider.output(node.getType())
//            }
//            is UnaryExpression -> {
//                InterpretUnaryExpression(version, outPutProvider, inputProvider, envProvider).interpret(
//                    node,
//                    storage
//                )
//            }
//        }
//    }
}