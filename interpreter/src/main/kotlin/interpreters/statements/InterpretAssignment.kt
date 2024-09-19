package interpreters.statements

import AssignmentStatement
import StoredValue
import interfaces.*
import interpreters.InterpretExpression
import results.InterpreterResultInformation
import results.InterpreterSuccess
import utils.Storage

class InterpretAssignment(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val version: String,
) {

    fun interpret(node: AssignmentStatement, storage: Storage): InterpreterResult {
        val identifier = node.getIdentifier().getIdentifier()
        val value = InterpretExpression(
            version,
            outPutProvider,
            inputProvider,
            envProvider,
        ).interpret(node.getValue(), storage)
        val valueToUse = convertToStoredValue(value)
        storage.addToStorage(identifier, valueToUse)
        return InterpreterResultInformation(storage)
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
}