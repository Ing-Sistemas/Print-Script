package org.example.parser.semantic.result

import BooleanValue
import NumberValue
import StoredValue
import StringValue
import org.example.parser.semantic.DataType

class ResultFactory {

    fun create(value: StoredValue, type: DataType, mutable: Boolean, errors: List<String> = emptyList()): ResultInformation {
        return when (value) {
            is StringValue -> createStringResult(value.value, type, mutable, errors)
            is NumberValue -> createNumberResult(value.value, type, mutable, errors)
            is BooleanValue -> createBooleanResult(value.value, type, mutable, errors)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun createStringResult(value: String, type: DataType, mutable: Boolean, errors: List<String> = emptyList()): ResultInformation {
        return ResultString(value, type, mutable, errors)
    }

    fun createNumberResult(value: Double, type: DataType, mutable: Boolean, errors: List<String> = emptyList()): ResultInformation {
        return ResultNumber(value, type, mutable, errors)
    }

    fun createBooleanResult(value: Boolean, type: DataType, mutable: Boolean, errors: List<String> = emptyList()): ResultInformation {
        return ResultBoolean(value, type, mutable, errors)
    }

    fun createError(errorMessage: String): ResultInformation {
        return createStringResult("", DataType.STRING, false, listOf(errorMessage))
    }
}