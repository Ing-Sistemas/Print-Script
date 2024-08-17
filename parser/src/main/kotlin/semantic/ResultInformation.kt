package org.example.parser.semantic

class ResultInformation(
    private val value: String?,
    private val type: String?,
    private val errors: List<Any>)
{
    fun getValue(): String? {
        return value
    }
    fun getType(): String? {
        return type
    }
    fun getErrors(): List<Any> {
        return errors
    }

}
