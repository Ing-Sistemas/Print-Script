package org.example.parser.semantic

import BooleanValue
import NumberValue
import StoredValue
import StringValue
import VariableDeclarationStatement
import Visitor
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation

class TypeCheck(private val resultFactory: ResultFactory) {

    fun checkVariableDeclaration(
        node: VariableDeclarationStatement,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
        val assignmentResult = node.getAssignmentExpression()?.accept(visitor)
            ?: return resultFactory.createError("Assignment result is null")

        if (assignmentResult.getErrors().isNotEmpty()) return assignmentResult

        val declarationNodeType = node.getTypeDeclarationExpression().getType()

        return if (declarationNodeType != assignmentResult.getType().toString()) {
            resultFactory.createError("Type mismatch for var dec")
        } else {
            resultFactory.create(convertToStoredValue(assignmentResult), assignmentResult.getType())
        }
    }

    private fun convertToStoredValue(result: ResultInformation): StoredValue {
        return when (result.getType()) {
            DataType.STRING -> StringValue(result.getValue())
            DataType.NUMBER -> NumberValue(result.getValue())
            DataType.BOOLEAN -> BooleanValue(result.getValue())
        }
    }
}