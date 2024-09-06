package org.example.parser.semantic.result

import org.example.parser.semantic.DataType

class ResultFactory {

    fun create(value: Any, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return when (value) {
            is String -> createStringResult(value, type, errors)
            is Double -> createNumberResult(value, type, errors)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun createStringResult(value: String, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultString(value, type, errors)
    }

    fun createNumberResult(value: Double, type: DataType, errors: List<String> = emptyList()): ResultInformation {
        return ResultNumber(value, type, errors)
    }

    fun createError(errorMessage: String): ResultInformation {
        return createStringResult("", DataType.STRING, listOf(errorMessage))
    }

    fun mergeResults(vararg results: ResultInformation): ResultInformation {
        val errors = results.flatMap { it.getErrors() }
        return create(results.last().getValue(), results.last().getType(), errors)
    }
}