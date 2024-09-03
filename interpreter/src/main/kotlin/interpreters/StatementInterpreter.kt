package org.example.interpreters

import Visitor

class StatementInterpreter : Interpreter {
    override fun <T> accept(visitor: Visitor<T>): T {
        TODO("Not yet implemented")
    }
}