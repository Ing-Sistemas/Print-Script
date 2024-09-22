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
) {

    fun interpret(node: EmptyVarDeclarationStatement, storage: Storage): InterpreterResult {
        val declarator = node.getDeclarator()
        storage.addToStorage(declarator, null)
        return InterpreterSuccess(storage.getFromStorage(declarator))
    }
}