package utils

import StoredValue

class Storage {
    private val storage = mutableMapOf<String, StoredValue>()

    fun getStorage() = storage
    fun addToStorage(key: String, value: StoredValue) {
        storage[key] = value
    }
    fun getFromStorage(key: String) = storage[key]
}