//package interpreters
//
//import ReadEnvNode
//import interfaces.EnvProvider
//import interfaces.InputProvider
//import interfaces.InterpreterResult
//import interfaces.OutPutProvider
//import results.InterpreterResultInformation
//import utils.Storage
//
//class InterpretReadEnv(
//    private val version: String,
//    private val outPutProvider: OutPutProvider,
//    private val inputProvider: InputProvider,
//    private val envProvider: EnvProvider,
//) {
//
//    fun interpret(node: ReadEnvNode, storage: Storage): InterpreterResult {
//        val env = envProvider.getEnv(node.getIdentifierName())
//        if (env.isNullOrBlank())
//        storage.addToStorage(node.getIdentifierName(),
//        return InterpreterResultInformation(storage)
//    }
//}