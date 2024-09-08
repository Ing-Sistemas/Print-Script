package org.example.parser.semantic

import AssignmentStatement
import IdentifierExpression
import StoredValue
import StringValue
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
            val value = StringValue(identifier)
            val type = storage[identifier]!!.getType()
            result.create(value, parseToDataType(type))
            } else {
            result.create(StringValue(identifier), DataType.STRING)
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

    private fun storeVariableCorrectly(
        identifierResult: ResultInformation,
        valueResult: ResultInformation,
    ) {
        val identifier = identifierResult.getValue<String>()
        storage[identifier] = valueResult.getValue()
    }

    private fun parseToDataType(type: String): DataType {
        return when (type) {
            "string" -> DataType.STRING
            "number" -> DataType.NUMBER
            //"boolean" -> DataType.
            else -> DataType.STRING
        }
    }
}