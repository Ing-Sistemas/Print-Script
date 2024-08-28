package org.example.parser.semantic

class ResultFactory {
    fun create(value: String?, type: String?, errors: List<String> = emptyList()): ResultInformation {
        return ResultInformation(value, type, errors)
    }

    fun createError(errorMessage: String): ResultInformation {
        return create(null, null, listOf(errorMessage))
    }

    fun mergeResults(vararg results: ResultInformation): ResultInformation {
        val errors = results.flatMap { it.getErrors() }
        return create(results.last().getValue(), results.last().getType(), errors)
    }
}