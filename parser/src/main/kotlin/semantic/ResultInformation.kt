package org.example.semantic

class ResultInformation(
    private val value: String?,
    private val type: String?,
    private val errors: List<String>,
) {
    fun getValue(): String? {
        return value
    }
    fun getType(): String? {
        return type
    }
    fun getErrors(): List<String> {
        return errors
    }
}