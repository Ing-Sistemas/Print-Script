package org.example.parser.semantic.result

import org.example.parser.semantic.DataType

class ResultBoolean(
    private val value: Boolean,
    private val type: DataType,
    private val mutable: Boolean,
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

    override fun getMutability(): Boolean {
        return mutable
    }
}