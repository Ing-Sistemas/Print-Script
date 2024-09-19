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
                if (useRight is InterpreterSuccess) {
                    val numberToUse = (useRight.getOriginalValue()) as Double
                    val negateNumber = -numberToUse
                    return InterpreterSuccess(toCustom(negateNumber))
                }
                else {
                    return InterpreterFailure("There is an interpreter Failure in: $useRight")
                }
            }
            else -> return InterpreterFailure("Unary operator ${node.getOperator()} not supported")
        }
    }
    private fun toCustom (value: Any?): NumberValue {
        return NumberValue(value as Double)
    }
}