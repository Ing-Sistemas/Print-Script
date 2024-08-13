package org.example

class Interpreter {
    private val storage = mutableMapOf<String, Any>()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            //is AssignmentNode -> visitAssignment(node)
            is LeafNode -> visitLeaf(node)
            is ProgramNode -> visitProgram(node)
            is VariableDeclarationNode -> visitVariableDeclaration(node)
            //is StatementNode -> visitStatement(node)
            //is TypeDeclarationNode -> visitTypeDeclaration(node)
            is LiteralNode -> visitLiteral(node)
            else -> throw IllegalArgumentException("Unknown node type: ${node.javaClass}")
        }
    }

    private fun visitLeaf(node: LeafNode): Any {
        return node.getToken().getValue()
    }

    private fun visitProgram(node: ProgramNode): Any {
        //TODO visitar todos los children y hacerles a cada uno interpret(child)
        TODO()
    }

    private fun visitVariableDeclaration(node: VariableDeclarationNode): Any {
        //asumo q lo guardamos: left: nombre, right: valor TODO checkear esto
        val variableName = (node.getChildren()[0] as LeafNode).getToken().getValue()
        val value = interpret(node.getChildren()[1])
        storage[variableName] = value
        return value
    }

    private fun visitLiteral(node: LiteralNode): Any {
        return node.getToken().getValue()
    }
}