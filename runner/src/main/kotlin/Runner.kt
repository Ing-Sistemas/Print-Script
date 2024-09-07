package org.example

import Lexer
import interpreters.Interpreter
import org.example.parser.Parser
import utils.Storage

class Runner {
    private val lexer = Lexer()
    private val parser = Parser()
    private val oldInterpreter = Interpreter()
    private val storage = Storage()

    fun run(code: String): Any {
        val tokens = lexer.tokenize(code)
        // println(tokens[12].getType())
        val ast = parser.parse(tokens)
        val interpreted = oldInterpreter.interpret(ast, storage)
        // val storage = interpreter.getStorage()
        // println(storage)
        // println(storage["a"])
        // println(storage["c"])
        return interpreted
    }
}