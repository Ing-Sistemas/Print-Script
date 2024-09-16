package org.example

import Lexer
import interpreters.Interpreter
import org.example.parser.Parser
import utils.Storage
import java.io.InputStream

class Runner {
    private val parser = Parser()
    private val interpreter = Interpreter()
    private val storage = Storage()
    private val versions = setOf("1.0", "1.1")

    fun run(inputStream: InputStream, version: String) {
        if (version !in versions) {
            throw IllegalArgumentException("version: '$version' is not supported")
        }

        val readerIterator = ReaderIterator().getLineIterator(inputStream)
        val tokens = Lexer(version).tokenize(readerIterator)
        while (tokens.hasNext()) {
            val token = tokens.next()
            println("${token.getType()} line: ${token.getPosition().getLine()}  col: ${token.getPosition().getColumn()}  ${token.getValue()}")
        }
    }
}