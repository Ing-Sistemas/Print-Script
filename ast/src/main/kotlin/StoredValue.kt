package com.printscript.ast

sealed interface StoredValue {
    fun getType(): String
    fun getMutability(): Boolean
    fun unMutable(): StoredValue
}

data class StringValue(val value: String, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "string"
    override fun getMutability() = isMutable
    override fun unMutable() = StringValue(value, false)
}

data class NumberValue(val value: Double, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "number"
    override fun getMutability() = isMutable
    override fun unMutable() = NumberValue(value, false)
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "boolean"
    override fun getMutability() = isMutable
    override fun unMutable() = BooleanValue(value, false)
}