sealed interface StoredValue {
    fun getType(): String
}

data class StringValue(val value: String, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "string"
}

data class NumberValue(val value: Number, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "number"
}

data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue {
    override fun getType() = "boolean"
}