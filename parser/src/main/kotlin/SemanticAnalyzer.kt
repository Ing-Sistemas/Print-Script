package org.example

import Token

class SemanticAnalyzer {
    fun analyze(node: ASTNode) {
        when (node) {
            //is ProgramNode -> TODO()
            is AssignmentNode -> analyzeAssignment(node)
            is BinaryNode -> analyzeBinary(node)


            else -> throw Exception("Unknown node type: ${node.javaClass}")
        }
    }

    private fun analyzeAssignment(node: AssignmentNode) {
        val identifier = node.getIdentifierNode()
        val literal = node.getLiteralNode()

        compareTypes(identifier.getToken(), literal.getToken())
    }

    private fun analyzeBinary(node: BinaryNode) {
        val left = node.getLeft()
        val right = node.getRight()

        compareTypes(left.getToken(), right.getToken())
    }





    private fun compareTypes(left: Token, right: Token) {
        if (left.getType() != right.getType()) {
            throw Exception("Type mismatch: ${left.getType()} != ${right.getType()}")
        }
    }


}