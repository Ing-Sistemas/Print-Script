package utils

class Storage {
    private val storage = mutableMapOf<String, Any>()

    fun getStorage() = storage
    fun addToStorage(key: String, value: Any) {
        storage[key] = value
    }
    fun getFromStorage(key: String) = storage[key]
}