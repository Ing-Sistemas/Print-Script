package org.example

import Lexer
import org.example.parser.Parser

class Runner {
    private val lexer = Lexer()
    private val parser = Parser()
    private val interpreter = Interpreter()

    fun run(code: String): Any {
        val tokens = lexer.tokenize(code)
        // println(tokens[12].getType())
        val ast = parser.parse(tokens)
        val interpreted = interpreter.interpret(ast)
        // val storage = interpreter.getStorage()
        // println(storage)
        // println(storage["a"])
        // println(storage["c"])
        return interpreted
    }
}