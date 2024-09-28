package com.printscript.parser.semantic.result

import com.printscript.parser.semantic.DataType

sealed interface ResultInformation {
    fun <T> getValue(): T
    fun getType(): DataType
    fun getErrors(): List<String>
}