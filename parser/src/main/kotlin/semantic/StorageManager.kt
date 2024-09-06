package org.example.parser.semantic

import AssignmentStatement
import IdentifierExpression
import StoredValue
import Visitor
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation

class StorageManager(
    private val result: ResultFactory,
) {
    private val storage = mutableMapOf<String, StoredValue>()

    fun getIdentifierResult(node: IdentifierExpression): ResultInformation {
        val identifier = node.getIdentifier()
        return if (identifier in storage) {
            val type = DataType.STRING
            result.createStringResult(varName, type)
            }
            else {
                result.create(varName, null)
            }
        }
    }

    fun handleAssignment(
        node: AssignmentStatement,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val identifierResult = node.getIdentifier().accept(visitor)
        val valueResult = node.getValue().accept(visitor)
        storeVariableCorrectly(identifierResult, valueResult)
        return result.mergeResults(identifierResult, valueResult)
    }

    private fun tryToInt(value: String): String {
        return value.toIntOrNull()?.toString() ?: value
    }

    private fun storeVariableCorrectly(
        identifierResult: ResultInformation,
        valueResult: ResultInformation,
    ) {
        val identifier = identifierResult.getValue() as String
        val value = valueResult.getValue() as String
        val type = valueResult.getType() as String
        when (type) {
            "LITERAL_STRING" -> {
                stringStorage[identifier] = value
            }
            "LITERAL_NUMBER" -> {
                doubleStorage[identifier] = value.toDouble()
            }
            else -> {
                stringStorage[identifier] = tryToInt(value)
            }
        }
    }
}