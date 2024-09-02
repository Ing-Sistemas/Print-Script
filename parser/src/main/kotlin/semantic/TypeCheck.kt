package org.example.parser.semantic

class TypeCheck(private val resultFactory: ResultFactory) {

    fun checkVariableDeclaration(
        node: VariableDeclarationNode,
        visitor: Visitor<ResultInformation>,
    ): ResultInformation {
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