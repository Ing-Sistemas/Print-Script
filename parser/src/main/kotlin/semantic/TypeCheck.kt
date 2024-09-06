package org.example.parser.semantic

import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultString

class TypeCheck(private val resultFactory: ResultFactory) {

    fun checkVariableDeclaration(
        node: VariableDeclarationNode,
        visitor: Visitor<ResultString>,
    ): ResultString {
        val assignmentResult = node.getAssignment().accept(visitor)
        if (assignmentResult.getErrors().isNotEmpty()) return assignmentResult

        val declarationNodeType = node.getTypeDeclaration().getValue()
        val actualType = if (assignmentResult.getType() == "LITERAL_NUMBER") "number" else "string"

        return if (declarationNodeType != actualType) {
            resultFactory.createError("Type mismatch for var dec")
        } else {
            resultFactory.create(assignmentResult.getValue(), assignmentResult.getType())
        }
    }
}