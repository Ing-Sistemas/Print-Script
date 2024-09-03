package org.example.interpreters

import Visitor

interface Interpreter {
    fun <T> accept(visitor: Visitor<T>): T
}