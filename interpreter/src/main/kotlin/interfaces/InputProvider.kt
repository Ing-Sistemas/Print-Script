package com.printscript.interpreter.interfaces

interface InputProvider {
    fun readInput(name: String): String?
}