package interpreters.statements

import BooleanValue
import Interpreter
import NumberValue
import StoredValue
import StringValue
import VariableDeclarationStatement
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import interpreters.InterpretExpression
import utils.InterpreterFailure
import utils.InterpreterResultInformation
import utils.InterpreterSuccess
import utils.Storage

class InterpretVariableDeclaration (
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val version: String
) {

    fun interpret(node: VariableDeclarationStatement, storage: Storage) : InterpreterResult {
        when (version) {
            "1.1" -> {
                val declarator = node.getDeclarator()
                val value = node.getAssignmentExpression().getValue()
                val storedValue = convertToStoredValue(InterpretExpression(
                    version,
                    outPutProvider,
                    inputProvider,
                    envProvider).interpret(value, storage))
                storage.addToStorage(declarator, storedValue)
                return InterpreterResultInformation(storage)
            }
            "1.0" -> {
                if (node.getDeclarator() == "const"){
                    return InterpreterFailure("Const declaration is not supported in this version: $version")
                }
                else {
                    val declarator = node.getDeclarator()
                    val value = node.getAssignmentExpression().getValue()
                    val storedValue = convertToStoredValue(InterpretExpression(
                        version,
                        outPutProvider,
                        inputProvider,
                        envProvider).interpret(value, storage))
                    storage.addToStorage(declarator, storedValue)
                    return InterpreterResultInformation(storage)
                }
            }
            else -> {
                return InterpreterFailure("Variable declaration is not supported in this version: $version")
            }
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
}