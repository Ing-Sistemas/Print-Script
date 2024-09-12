package org.example

import Lexer
import Token
import interfaces.OutPut
import interpreters.Interpreter
import org.example.parser.Parser
import utils.Storage
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class Runner {
    private val parser = Parser()
    private val interpreter = Interpreter()
    private val storage = Storage()
    private val versions = setOf("1.0", "1.1")

    fun run(input: String, version : String): Any{
        if (version !in versions) {
            throw IllegalArgumentException("version: '$version' is not supported")
        }

        val tokens = Lexer().tokenize(input)

        return interpreter.interpret(parser.parse(tokens), storage)
    }
}