package com.printscript.interpreter.interfaces

interface OutPutProvider {
    fun output(message: String): String {
        return message
    }
}