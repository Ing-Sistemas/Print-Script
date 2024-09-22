import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.*
import results.InterpreterFailure
import utils.Storage

class Interpreter(
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ASTNode, storage: Storage): InterpreterResult {
        return when (node) {
            is Literal -> {
                InterpretLiteral(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is Expression -> {
                InterpretExpression(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is Statement -> {
                InterpretStatement(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider,
                ).interpret(node, storage)
            }
            is ReadEnvNode -> {
                    InterpretReadEnv(
                        version,
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage)

            }
            is ReadInputNode -> {
                    InterpretReadInput(
                        version,
                        outPutProvider,
                        inputProvider,
                        envProvider,
                    ).interpret(node, storage)
            }
        }
    }
}