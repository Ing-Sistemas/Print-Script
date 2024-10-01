package com.printscript.interpreter.results

import com.printscript.interpreter.interfaces.Failure

class InterpreterFailure(override val error: String) : Failure {
    fun getErrorMessage(): String {
        return error
    }
}