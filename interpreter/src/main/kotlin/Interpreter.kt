package org.example

class Interpreter {
    private val storage = mutableMapOf<String, Int>()

    fun interpret(node: ASTNode) {
        return when (node) {
            is AssignmentNode -> visitAssignment(node)
            is CallNode -> TODO("Implement")
            is IdentifierNode -> TODO("Implement")
            is LeafNode -> TODO("Implement")
            is LiteralNode -> TODO("Implement")
            is ProgramNode -> TODO("Implement")
            is StatementNode -> TODO("Implement")
            is TypeDeclarationNode -> TODO("Implement")
            is VariableDeclarationNode ->
        }
    }

    private fun visitAssignment(node: AssignmentNode) : Int {
        TODO("Implement")
    }
}