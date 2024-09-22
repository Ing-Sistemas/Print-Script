//package interpreters
//
//import ReadInputNode
//import interfaces.*
//import results.InterpreterResultInformation
//import utils.Storage
//
//class InterpretReadInput(
//    private val version: String,
//    private val outPutProvider: OutPutProvider,
//    private val inputProvider: InputProvider,
//    private val envProvider: EnvProvider,
//) {
//    fun interpret(node: ReadInputNode, storage: Storage): InterpreterResult {
//        val input = inputProvider.readInput(node.getInput())
//        if (input != null) {
//            storage.addToStorage(input, null)
//        }
//        return InterpreterResultInformation(storage)
//    }
//}