package org.example

import Lexer

fun main() {
    val input = "let myVal: string = 'hello argh 76'"

    val listToken = Lexer(input).tokenize()
    for (elem in listToken) {
        println("${elem.getType()}: ${elem.getValue()} ")
    }
}