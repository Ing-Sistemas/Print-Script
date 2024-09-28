package com.printscript.interpreter.interpreters

import com.printscript.ast.ReadEnvNode
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.utils.Storage

class InterpretReadEnv(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    fun interpret(node: ReadEnvNode, storage: Storage): InterpreterResult {
//        val env = envProvider.getEnv(node.getIdentifierName())
//        if (env.isNullOrBlank())
//        storage.addToStorage(node.getIdentifierName(),
//        return InterpreterResultInformation(storage)
        TODO()
    }
}