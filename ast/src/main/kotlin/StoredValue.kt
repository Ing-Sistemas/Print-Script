sealed interface StoredValue {
    fun getType(): String
}

data class StringValue(val value: String, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "string"
}

data class DoubleValue(val value: Double, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "number"
    fun getValue() = value
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "boolean"
}