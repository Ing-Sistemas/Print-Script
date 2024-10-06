package com.printscript.interpreter.providers

import com.printscript.interpreter.interfaces.OutPutProvider

class DefaultOutPutProvider() : OutPutProvider {
    override fun output(message: String) {
        println(message)
    }
}