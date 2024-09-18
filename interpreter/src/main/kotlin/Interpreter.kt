import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import interpreters.InterpretLiteral
import interpreters.InterpretStatement
import utils.Storage

class Interpreter (
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider
    ){

    fun interpret(node: ASTNode, storage: Storage): InterpreterResult {
        return when (node) {
            is Literal -> {
                InterpretLiteral(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }
            is Expression -> {
                InterpretExpression(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }
            is Statement -> {
                InterpretStatement(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }
        }
    }
}