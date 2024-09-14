sealed interface StoredValue {
    fun getType(): String
}

data class StringValue(val value: String, val isMutable: Boolean) : StoredValue {
    override fun getType() = "string"
}

data class NumberValue(val value: Double, val isMutable: Boolean) : StoredValue {
    override fun getType() = "number"
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean) : StoredValue {
    override fun getType() = "boolean"
}

data class EmptyValue(val value: Nothing?, val isMutable: Boolean) : StoredValue {
    override fun getType() = "null"
}