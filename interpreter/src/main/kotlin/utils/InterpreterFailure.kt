package utils

import interfaces.Failure

class InterpreterFailure(override val error: String) : Failure {
    fun getErrorMessage(): String {
        return error
    }
}