package com.printscript.interpreter.interpreters.statements

import com.printscript.ast.EmptyVarDeclarationStatement
import com.printscript.interpreter.interfaces.*
import com.printscript.interpreter.results.InterpreterSuccess
import com.printscript.interpreter.utils.Storage

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