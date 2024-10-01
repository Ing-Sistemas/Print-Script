package com.printscript.ast

sealed interface StoredValue {
    fun getType(): String
    fun getMutability(): Boolean
}

data class StringValue(val value: String, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "string"
    override fun getMutability() = isMutable
}

data class NumberValue(val value: Double, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "number"
    override fun getMutability() = isMutable
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "boolean"
    override fun getMutability() = isMutable
}