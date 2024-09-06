sealed interface StoredValue {}

data class StringValue(val value: String, val isMutable: Boolean = true) : StoredValue
data class IntValue(val value: Int, val isMutable: Boolean = true) : StoredValue
data class DoubleValue(val value: Double, val isMutable: Boolean = true) : StoredValue
data class BooleanValue(val value: Boolean, val isMutable: Boolean = true) : StoredValue