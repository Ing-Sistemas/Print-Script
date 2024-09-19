package org.example

import Interpreter
import Lexer
import org.example.parser.Parser
import utils.DefaultEnvProvider
import utils.DefaultInputProvider
import utils.DefaultOutPutProvider
import utils.Storage
import java.io.InputStream

class Runner {
    private val parser = Parser()
    private val storage = Storage()
    private val versions = setOf("1.0", "1.1")
    private val interpreter = Interpreter(String.toString(), outPutProvider = DefaultOutPutProvider() , inputProvider = DefaultInputProvider(), envProvider = DefaultEnvProvider())

    fun run(inputStream: InputStream, version: String) {
        if (version !in versions) {
            throw IllegalArgumentException("version: '$version' is not supported")
        }

        val readerIterator = ReaderIterator().getLineIterator(inputStream)
        val tokens = Lexer(version).tokenize(readerIterator)
        interpreter.interpret(parser.parse(tokens), storage)
    }
}