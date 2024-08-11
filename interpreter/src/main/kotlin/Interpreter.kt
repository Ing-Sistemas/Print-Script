package org.example

class Interpreter {
    private val storage = mutableMapOf<String, Any>()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            is AssignmentNode -> visitAssignment(node)
            is CallNode -> TODO("Implement")
            is IdentifierNode -> TODO("Implement")
            is LeafNode -> TODO("Implement")
            is LiteralNode -> TODO("Implement")
            is ProgramNode -> TODO("Implement")
            is StatementNode -> TODO("Implement")
            is TypeDeclarationNode -> TODO("Implement")
            is VariableDeclarationNode -> TODO("Implement")
            else -> throw IllegalArgumentException("Unknown node type: ${node.javaClass}")
        }
    }

    private fun visitAssignment(node: AssignmentNode) : Any {
        TODO("Implement")
    }
}