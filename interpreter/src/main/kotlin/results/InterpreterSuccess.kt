package com.printscript.interpreter.results

import com.printscript.ast.BooleanValue
import com.printscript.ast.NumberValue
import com.printscript.ast.StoredValue
import com.printscript.ast.StringValue
import com.printscript.interpreter.interfaces.Success

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

    override fun getIntValue(): Any {
        return when (val value = customValue as StoredValue) {
            is NumberValue -> convertToIntIfApplicable(value)
            is StringValue -> convertToIntIfApplicable(value)
            is BooleanValue -> value.value
        }
    }

    private fun convertToIntIfApplicable(value: StoredValue): Any {
        if (value is StringValue) {
            val newValue = value.value
            return if (newValue.toDoubleOrNull() != null) {
                convertToIntIfApplicable(NumberValue(newValue.toDouble()))
            } else {
                newValue
            }
        }
        if (value is NumberValue) {
            val newValue = value.value
            return if (newValue % 1.0 == 0.0) {
                newValue.toInt()
            } else {
                newValue
            }
        }
        return value
    }
}