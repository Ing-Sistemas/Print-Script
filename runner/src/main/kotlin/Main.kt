package org.example

import Lexer
import interpreters.Interpreter
import org.example.output.Printer
import org.example.parser.Parser
import utils.Storage
import java.io.File

fun main() {
    val file = File("runner/src/main/resources/main.ps")
    val outPut = Printer()
    val token = Lexer().tokenize("println('hola');")
    val ast   = Parser().parse(token)
    val storage = Storage()
    val interpreter = Interpreter().interpret(ast, storage)
    println(interpreter)
}