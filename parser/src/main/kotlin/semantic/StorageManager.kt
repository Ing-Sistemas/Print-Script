package org.example.semantic

import org.example.AssignmentNode
import org.example.IdentifierNode
import org.example.Visitor

class StorageManager(
    private val result: ResultFactory,
) {
    private val storage = mutableMapOf<String, String>()

    fun getIdentifierResult(node: IdentifierNode): ResultInformation {
        val varName = node.getValue()
        return if (varName in storage) {
            val type = getTypeForVar(storage[varName])
            result.create(varName, type)
        } else {
            result.create(varName, null)
        }
    }

    fun get(key: String): Any? {
        val value = storage[key]
        return when {
            value == null -> null
            value.toIntOrNull() != null -> value.toInt()
            else -> value
        }
    }

    fun handleAssignment(
        node: AssignmentNode,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val identifierResult = node.getIdentifierNode().accept(visitor)
        val valueResult = node.getValueNode().accept(visitor)
        storage[identifierResult.getValue().toString()] = tryToInt(valueResult.getValue().toString())
        return result.mergeResults(identifierResult, valueResult)
    }

    private fun getTypeForVar(value: String?): String {
        return when (value?.toIntOrNull()) {
            is Int -> "LITERAL_NUMBER"
            else -> "LITERAL_STRING"
        }
    }

    private fun tryToInt(value: String): String {
        return value.toIntOrNull()?.toString() ?: value
    }
}