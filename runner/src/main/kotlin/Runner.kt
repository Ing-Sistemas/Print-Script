package org.example

import Interpreter
import Lexer
import interfaces.EnvProvider
import interfaces.InputProvider
import interfaces.InterpreterResult
import interfaces.OutPutProvider
import org.example.parser.ASTIterator
import org.example.parser.Parser
import results.InterpreterFailure
import utils.Storage
import java.io.InputStream

class Runner(
    inputProvider: InputProvider,
    outPutProvider: OutPutProvider,
    envProvider: EnvProvider,
) {
    private val parser = Parser()
    private val storage = Storage()
    private val versions = setOf("1.0", "1.1")
    private val interpreter = Interpreter(outPutProvider, inputProvider, envProvider)

    fun run(inputStream: InputStream, version: String): InterpreterResult {
        if (version !in versions) {
            throw IllegalArgumentException("version: '$version' is not supported")
        }

        val readerIterator = ReaderIterator().getLineIterator(inputStream)
        val tokens = Lexer(version).tokenize(readerIterator)
        var result: InterpreterResult = InterpreterFailure("")
        val astIterator = ASTIterator(tokens, parser)
        while (astIterator.hasNext()) {
            val ast = astIterator.next()
            println(ast.toString())
            result = interpreter.interpret(ast, storage)
        }
        return result
//        while (astIterator.hasNext()) {
//            val result = interpreter.interpret(astIterator.next(), storage)
//            println(result)
//        }
    }
}