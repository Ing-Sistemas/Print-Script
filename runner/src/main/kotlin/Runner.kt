package com.printscript.runner

import com.printscript.interpreter.Interpreter
import com.printscript.interpreter.interfaces.EnvProvider
import com.printscript.interpreter.interfaces.InputProvider
import com.printscript.interpreter.interfaces.OutPutProvider
import com.printscript.interpreter.utils.Storage
import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
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

    fun run(inputStream: InputStream, version: String): List<String> {
        if (version !in versions) {
            throw IllegalArgumentException("version: '$version' is not supported")
        }

        val readerIterator = ReaderIterator().getLineIterator(inputStream)
        val tokens = Lexer(version).tokenize(readerIterator)
        var result = emptyList<String>()
        val astIterator = ASTIterator(tokens, parser)
        while (astIterator.hasNext()) {
            val ast = astIterator.next()
            result = interpreter.interpret(ast, storage)
        }
        return result
    }
}