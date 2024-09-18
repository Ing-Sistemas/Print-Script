package interpreters.expressions

import ASTNode
import NumberValue
import UnaryExpression
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import utils.InterpreterFailure
import utils.InterpreterSuccess
import utils.Storage

class InterpretUnaryExpression (
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider
){

    fun interpret(node: ASTNode, storage: Storage) : InterpreterResult {
        val unaryNode = node as UnaryExpression
        val right = unaryNode.getRight()
        val useRight = InterpretExpression(version, outPutProvider, inputProvider, envProvider).interpret(right, storage)
        if (useRight is InterpreterSuccess && useRight.getSuccess() !is NumberValue) {
            return InterpreterFailure("Unary expression must be a number")
        }
        when (unaryNode.getOperator()) {
            "-" -> {
                val numberToUse = (toDouble(useRight)).unaryMinus()
                return InterpreterSuccess(NumberValue(numberToUse))
            }
            else -> return InterpreterFailure("Unary operator ${node.getOperator()} not supported")
        }
    }
    private fun toDouble (number: Any) : Double {
        return when (number) {
            is NumberValue -> {
                number.value
            }
            else -> throw IllegalArgumentException("Value is not a number")
        }
    }
}