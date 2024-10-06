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

data class NumberValue(val value: Number, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "number"
    override fun getMutability() = isMutable
    override fun unMutable() = NumberValue(value, false)

    operator fun plus(other: NumberValue): NumberValue {
        val result = (this.value.toDouble() ?: 0.0) + (other.value.toDouble() ?: 0.0)
        return NumberValue(if (result % 1 == 0.0) result.toInt() else result)
    }
    operator fun minus(other: NumberValue): NumberValue {
        val result = (this.value.toDouble() ?: 0.0) - (other.value.toDouble() ?: 0.0)
        return NumberValue(if (result % 1 == 0.0) result.toInt() else result)
    }
    operator fun times(other: NumberValue): NumberValue {
        val result = (this.value.toDouble() ?: 0.0) * (other.value.toDouble() ?: 0.0)
        return NumberValue(if (result % 1 == 0.0) result.toInt() else result)
    }
    operator fun div(other: NumberValue): NumberValue {
        val result = (this.value.toDouble() ?: 0.0) / (other.value.toDouble() ?: 0.0)
        return NumberValue(if (result % 1 == 0.0) result.toInt() else result)
    }
    fun negate(): NumberValue {
        val result = -(this.value.toDouble() ?: 0.0)
        return NumberValue(if (result % 1 == 0.0) result.toInt() else result)
    }
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "boolean"
    override fun getMutability() = isMutable
    override fun unMutable() = BooleanValue(value, false)
}