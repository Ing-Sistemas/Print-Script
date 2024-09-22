package interfaces

interface OutPutProvider {
    fun output(message: String): String {
        return message
    }
}