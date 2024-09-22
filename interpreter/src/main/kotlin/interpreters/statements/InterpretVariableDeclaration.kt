package interpreters.statements

import StoredValue
import VariableDeclarationStatement
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import results.InterpreterFailure
import results.InterpreterResultInformation
import results.InterpreterSuccess
import utils.Storage

class InterpretVariableDeclaration(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: VariableDeclarationStatement, storage: Storage): InterpreterResult {
        val declarator = node.getDeclarator()
        val value = node.getAssignmentExpression().getValue()
        val storedValue = convertToStoredValue(
            InterpretExpression(
                outPutProvider,
                inputProvider,
                envProvider,
                ).interpret(value, storage),
            )
        storage.addToStorage(declarator, storedValue)
        return InterpreterResultInformation(storage)
        }
    }

    private fun convertToStoredValue(value: InterpreterResult): StoredValue {
        return when (value) {
            is InterpreterSuccess -> {
                value.getSuccess() as StoredValue
            }
            else -> {
                throw IllegalArgumentException("Value is not a stored value")
            }
        }
    }