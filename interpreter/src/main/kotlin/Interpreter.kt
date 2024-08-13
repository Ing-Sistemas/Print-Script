package org.example

class Interpreter {
    private val storage = mutableMapOf<String, Any>()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            is AssignmentNode -> visitAssignment(node) //not ready
            is CallNode -> visitCall(node)
            is IdentifierNode -> visitIdentifier(node)
            is LeafNode -> TODO("Implement")
            is LiteralNode -> visitLiteral(node)
            is ProgramNode -> visitProgram(node) //ready
            is StatementNode -> visitStatement(node) //ready
            is TypeDeclarationNode -> TODO("Implement")
            is VariableDeclarationNode -> visitVariableDeclaration(node)
            else -> throw IllegalArgumentException("Unknown node type: ${node.javaClass}")
        }
    }

    private fun visitProgram(node: ProgramNode) {
        if (node.getChildren().isEmpty()) {
            throw IllegalArgumentException("Program is empty")
        }
        node.getChildren().forEach {
            interpret(it)
        }
    }

    private fun visitStatement(node: StatementNode){
        node.getChildren().forEach {
            if (it is AssignmentNode) {
                visitAssignment(it)
            }
            if (it is CallNode) {
                visitCall(it)
            }
            if (it is VariableDeclarationNode) {
                visitVariableDeclaration(it)
            }
        }
    }

    private fun visitVariableDeclaration(node: VariableDeclarationNode) {
        var key: Any? = null
        var value: Any? = null

        node.getChildren().forEach {
            if (it is IdentifierNode){
                key = visitIdentifier(it)
            }
            if (it is LiteralNode) {
                value = visitLiteral(it)
            }
        }
        storage [key.toString()] = value!!
    }

    private fun visitLiteral(node: LiteralNode): Any {
        return node.getToken().getValue()
    }

    private fun visitIdentifier(it: IdentifierNode): Any{
        return it.getToken().getValue()
    }


    private fun visitCall(node: CallNode) {
        TODO("Not yet implemented")
    }

    private fun visitAssignment(node: AssignmentNode)  {
//        node.getChildren().forEach {
//          if (it is LiteralNode) {
//            storage[it.getToken().getValue()] = visitLiteral(it)
//          }
//          if (it is TypeDeclarationNode) {
//            storage[it.getToken().getValue()] = visitTypeDeclaration(it)
//          }
//        }
    }

}