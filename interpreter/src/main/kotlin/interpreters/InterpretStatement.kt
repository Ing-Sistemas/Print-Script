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
import results.InterpreterFailure
import utils.Storage

class InterpretStatement(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: Statement, storage: Storage): InterpreterResult {
        return when (node) {
            is VariableDeclarationStatement -> {
                InterpretVariableDeclaration(
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }

            is FunctionCallStatement -> {
                InterpretFunctionCall(
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }

            is AssignmentStatement -> {
                InterpretAssignment(
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }

            is EmptyVarDeclarationStatement -> {
                InterpretEmptyVariable(
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }

            is IfStatement -> {
                InterpretIf(
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(node, storage)
            }
        }
    }
}