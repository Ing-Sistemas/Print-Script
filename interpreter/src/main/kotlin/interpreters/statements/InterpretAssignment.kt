package interpreters.statements

import AssignmentStatement
import BooleanValue
import NumberValue
import StoredValue
import StringValue
import interfaces.*
import interpreters.InterpretExpression
import utils.InterpreterResultInformation
import utils.Storage

class InterpretAssignment (
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val version: String
) {

    fun interpret (node: AssignmentStatement, storage: Storage) : InterpreterResult {
        val identifier = node.getIdentifier().getIdentifier()
        val value = InterpretExpression(
            version,
            outPutProvider,
            inputProvider,
            envProvider).interpret(node.getValue(), storage)
        val valueToUse = convertToStoredValue(value)
        storage.addToStorage(identifier, valueToUse)
        return InterpreterResultInformation(storage)
    }

    private fun convertToStoredValue(value: Any): StoredValue {
        return when (value) {
            is Double -> NumberValue(value)
            is String -> StringValue(value)
            is Boolean -> BooleanValue(value)
            else -> throw IllegalArgumentException("Invalid value type")
        }
    }
}