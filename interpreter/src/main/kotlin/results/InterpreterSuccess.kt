package results

import BooleanValue
import NumberValue
import StoredValue
import StringValue
import interfaces.Success

class InterpreterSuccess(override val customValue: StoredValue?) : Success {
    override fun getSuccess(): StoredValue? {
        return customValue
    }
    override fun getOriginalValue(): Any? {
        return when (val value = customValue as StoredValue) {
            is StringValue -> value.value
            is NumberValue -> value.value
            is BooleanValue -> value.value
            else -> null
        }
    }
}