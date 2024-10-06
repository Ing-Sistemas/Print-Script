package com.printscript.interpreter.utils

import com.printscript.ast.StoredValue

class Storage {
    private val storage = mutableMapOf<String, StoredValue?>()

    fun addToStorage(key: String, value: StoredValue?) {
        storage[key] = value
    }
    fun getFromStorage(key: String) = storage[key]
    fun deleteFromStorage(key: String) = storage.remove(key)
    fun getAllFromStorage() {
        for ((key, value) in storage) {
            println("$key: $value")
        }
    }
}