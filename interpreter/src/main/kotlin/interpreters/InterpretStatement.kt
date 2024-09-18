package interpreters

import AssignmentStatement
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IfStatement
import Statement
import VariableDeclarationStatement
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.statements.*
import utils.InterpreterFailure
import utils.Storage

class InterpretStatement(
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider
    ) {

    fun interpret(node: Statement, storage: Storage) : InterpreterResult {
        return when (node) {
            is VariableDeclarationStatement -> {
                InterpretVariableDeclaration(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                    version).interpret(node, storage)
            }

            is FunctionCallStatement -> {
                InterpretFunctionCall(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                    version).interpret(node, storage)
                }

            is AssignmentStatement -> {
                 InterpretAssignment(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                    version).interpret(node, storage)
            }

            is EmptyVarDeclarationStatement -> {
                 InterpretEmptyVariable(
                    outPutProvider,
                    inputProvider,
                    envProvider,
                    version).interpret(node, storage)
            }

            is IfStatement -> {
                if (version == "1.1") {
                     InterpretIf(
                        version,
                        outPutProvider,
                        inputProvider,
                        envProvider).interpret(node, storage)
                } else {
                    return InterpreterFailure("If is not supported in version: $version")
                }
            }
        }
    }
}