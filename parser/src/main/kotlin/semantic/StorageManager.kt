package org.example.parser.semantic

import AssignmentStatement
import BooleanValue
import EmptyValue
import IdentifierExpression
import NumberValue
import StoredValue
import StringValue
import VariableDeclarationStatement
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
        isMutable: Boolean,
        visitor: VisitorSemantic<ResultInformation>,
    ): ResultInformation {
        val identifierResult = node.getIdentifier().accept(visitor)
        val valueResult = node.getValue().accept(visitor)
        updateVariable(identifierResult, valueResult)
        return valueResult
    }

    fun handleVariableDeclaration(
        node: VariableDeclarationStatement,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val declarator = node.getDeclarator()
        val isMutable = declarator != "const"
        val assignmentResult = node.getAssignmentExpression().accept(visitor)
        val identifier = node.getAssignmentExpression().getIdentifier().getIdentifier()
        val typeForVariable = node.getTypeDeclarationExpression().getType()

        if (assignmentResult.getErrors().isNotEmpty()) return assignmentResult

        if (isMutable) { // si es true aka not-const
            storage[identifier] = convertToStoredValue(assignmentResult, true)
        } else {
            storage[identifier] = convertToStoredValue(assignmentResult, false)
        }

        return if (typeForVariable != assignmentResult.getType().toString()) {
            result.createError("Type mismatch for var dec")
        } else {
            result.create(convertToStoredValue(assignmentResult, isMutable), assignmentResult.getType())
        }
    }

    private fun updateVariable(
        identifierResult: ResultInformation,
        valueResult: ResultInformation,
    ) {
        // directly assume mutability
        val identifier = identifierResult.getValue<String>()
        val storedValue = storage[identifier]
        if (storedValue?.getMutability() == false) return
        storage[identifier] = valueResult.getValue()
    }

    private fun parseToDataType(type: String): DataType {
        return when (type) {
            "string" -> DataType.STRING
            "number" -> DataType.NUMBER
            "boolean" -> DataType.BOOLEAN
            "null" -> DataType.NULL
            else -> DataType.STRING
        }
    }

    private fun convertToStoredValue(result: ResultInformation, isMutable: Boolean): StoredValue {
        return when (result.getType()) {
            DataType.STRING -> StringValue(result.getValue(), isMutable)
            DataType.NUMBER -> NumberValue(result.getValue(), isMutable)
            DataType.BOOLEAN -> BooleanValue(result.getValue(), isMutable)
            DataType.NULL -> EmptyValue(result.getValue(), isMutable)
        }
    }
}