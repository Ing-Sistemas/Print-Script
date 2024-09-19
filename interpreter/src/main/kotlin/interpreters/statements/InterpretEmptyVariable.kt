package interpreters.statements

import EmptyVarDeclarationStatement
import interfaces.*
import results.InterpreterFailure
import results.InterpreterSuccess
import utils.Storage

class InterpretEmptyVariable(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val version: String,
) {

    fun interpret(node: EmptyVarDeclarationStatement, storage: Storage): InterpreterResult {
        when (version) {
            "1.1" -> {
                val declarator = node.getDeclarator()
                storage.addToStorage(declarator, null)
                return InterpreterSuccess(storage.getFromStorage(declarator))
            }
            else -> {
                return InterpreterFailure("Empty variable declaration is not supported in this version: $version")
            }
        }
    }
}