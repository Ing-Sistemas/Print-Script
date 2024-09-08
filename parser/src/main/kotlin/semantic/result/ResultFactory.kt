package org.example.parser.semantic.result

import BooleanValue
import DoubleValue
import StoredValue
import StringValue
import org.example.parser.semantic.DataType

class ResultFactory {

    fun create(value: StoredValue, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        println("arransamsam")
        return when (value) {
            is StringValue -> createStringResult(value.value, type, errors)
            is DoubleValue -> createNumberResult(value.value, type, errors)
            is BooleanValue -> createBooleanResult(value.value, type, errors)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun createStringResult(value: String, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultString(value, type, errors)
    }

    fun createNumberResult(value: Double, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultNumber(value, type, errors)
    }

    fun createBooleanResult(value: Boolean, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultBoolean(value, type, errors)
    }

    fun createError(errorMessage: String): ResultInformation {
        return createStringResult("", DataType.STRING, listOf(errorMessage))
    }

    fun mergeResults(vararg results: ResultInformation): ResultInformation {
        val errors = results.flatMap { it.getErrors() }
        //if (errors.isNotEmpty()) return createError(errors.joinToString("; "))
        val lastResult = results.last()
        return create(lastResult.getValue(), lastResult.getType(), errors)
    }
}