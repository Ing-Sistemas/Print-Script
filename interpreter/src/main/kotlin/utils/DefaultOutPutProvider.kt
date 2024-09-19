package utils

import interfaces.OutPutProvider

class DefaultOutPutProvider() : OutPutProvider {
    override fun output(message: String): String {
        return message
    }
}