package results

import interfaces.ResultInformation
import utils.Storage

class InterpreterResultInformation(override val result: Storage) : ResultInformation {
    fun getInformation(): Storage {
        return result
    }
}