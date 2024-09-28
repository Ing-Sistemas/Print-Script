package com.printscript.interpreter.providers

import com.printscript.interpreter.interfaces.InputProvider

class DefaultInputProvider : InputProvider {
    override fun readInput(name: String): String {
        val input = readlnOrNull() ?: ""
        return input
    }
}