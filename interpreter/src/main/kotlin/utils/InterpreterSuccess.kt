package utils

import StoredValue
import interfaces.Success

class InterpreterSuccess(override val customValue: StoredValue?): Success {
    fun getSuccess(): StoredValue? {
        return customValue
    }
}