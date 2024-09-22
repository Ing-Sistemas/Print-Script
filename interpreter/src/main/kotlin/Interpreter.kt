import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.*
import utils.Storage

class Interpreter(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ASTNode, storage: Storage): InterpreterResult {
        return when (node) {
            is Literal -> {
                InterpretLiteral(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is Expression -> {
                InterpretExpression(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is Statement -> {
                InterpretStatement(
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