package org.example.parser.semantic.result

import org.example.parser.semantic.DataType

sealed interface ResultInformation {
    fun <T> getValue(): T
    fun getType(): DataType
    fun getErrors(): List<String>
    fun getMutability(): Boolean
}