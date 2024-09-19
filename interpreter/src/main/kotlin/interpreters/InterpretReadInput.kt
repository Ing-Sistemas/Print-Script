package interpreters

import ReadInputNode
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import utils.Storage

class InterpretReadInput(
    private val version: String,
    private val outPutProvider: OutPutProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {
    fun interpret(node: ReadInputNode, storage: Storage): InterpreterResult {
        TODO()
    }
}