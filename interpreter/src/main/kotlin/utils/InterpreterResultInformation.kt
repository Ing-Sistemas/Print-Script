package utils

import StoredValue
import interfaces.ResultInformation

class InterpreterResultInformation(override val result: Storage) : ResultInformation {
    fun getInformation(): Storage {
        return result
    }
}