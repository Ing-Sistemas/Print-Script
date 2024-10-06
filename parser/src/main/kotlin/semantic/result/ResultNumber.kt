package com.printscript.parser.semantic.result

import com.printscript.parser.semantic.DataType

class ResultNumber(
    private val value: Number,
    private val type: DataType,
    private val errors: List<String>,
) : ResultInformation {
    override fun <T> getValue(): T {
        return value as T
    }

    override fun getType(): DataType {
        return type
    }

    override fun getErrors(): List<String> {
        return errors
    }
}