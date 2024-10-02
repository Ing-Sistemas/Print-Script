package com.printscript.interpreter.interpreters

import com.printscript.ast.ReadInputNode
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.InterpreterResult
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.utils.Storage

class InterpretReadInput(
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {
    fun interpret(node: ReadInputNode, storage: Storage): InterpreterResult {
//        val input = inputProvider.readInput(node.getPrompt())
//        if (input != null) {
//            storage.addToStorage(input, null)
//        }
//        return InterpreterResultInformation(storage)
        TODO()
    }
}