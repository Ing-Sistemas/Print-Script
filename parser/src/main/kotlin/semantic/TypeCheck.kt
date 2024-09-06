package org.example.parser.semantic

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
            resultFactory.create(assignmentResult.getValue(), assignmentResult.getType())
        }
    }
}