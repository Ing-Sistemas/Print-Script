package com.printscript.parser.semantic.result

import com.printscript.ast.BooleanValue
import com.printscript.ast.NumberValue
import com.printscript.ast.StoredValue
import com.printscript.ast.StringValue
import com.printscript.parser.semantic.DataType

class ResultFactory {

    fun create(value: StoredValue, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return when (value) {
            is StringValue -> createStringResult(value.value, type, errors)
            is NumberValue -> createNumberResult(value.value, type, errors)
            is BooleanValue -> createBooleanResult(value.value, type, errors)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun createStringResult(value: String, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultString(value, type, errors)
    }

    fun createNumberResult(value: Number, type: DataType, errors: List<String> = emptyList()): ResultInformation {
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
        val lastResult = results.last()
        return create(lastResult.getValue(), lastResult.getType(), errors)
    }
}