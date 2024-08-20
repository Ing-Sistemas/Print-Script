package org.example

class Interpreter {
    private val storage = mutableMapOf<String, Any>()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            is AssignmentNode -> visitAssignment(node)
            is CallNode -> visitCall(node)
            is IdentifierNode -> visitIdentifier(node)
            is LiteralNode -> visitLiteral(node)
            is ProgramNode -> visitProgram(node)
            is StatementNode -> visitStatement(node)
            is TypeDeclarationNode -> TODO("Implement")
            is VariableDeclarationNode -> visitVariableDeclaration(node)
            is BinaryNode -> evaluateNode(node)
            is UnaryNode -> TODO("Implement")
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
        when (val it = node.getStatement()) {
            is AssignmentNode -> visitAssignment(it)
            is CallNode -> visitCall(it)
            is VariableDeclarationNode -> visitVariableDeclaration(it)
            else -> {IllegalArgumentException()}
        }
    }

    private fun visitVariableDeclaration(node: VariableDeclarationNode) {
        val assignmentNode = node.getAssignment()
        val key = assignmentNode.getIdentifierNode().getValue()
        val value = evaluateNode(assignmentNode.getValueNode())
        storage[key] = value
    }

    //think valueChecker should be done better with value token and TokenType

    private fun evaluateNode(node: ASTNode): Any {
        return when (node) {
            is BinaryNode -> {
                val operator = node.getValue()
                val leftValue = evaluateNode(node.getLeft())
                val rightValue = evaluateNode(node.getRight())

                val left = leftValue.toString().toIntOrNull() ?: throw IllegalArgumentException("Invalid left")
                val right = rightValue.toString().toIntOrNull() ?: throw IllegalArgumentException("Invalid right")

                applyOperator(left, operator, right)
            }
            is LiteralNode -> {
                node.getValue().toIntOrNull() ?: node.getValue()
            }
            is IdentifierNode -> {
                storage[node.getValue()] ?: throw IllegalArgumentException("Unknown variable: ${node.getValue()}")
            }
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }

    private fun applyOperator (left: Int, operator: String, right: Int): Int {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> {throw IllegalArgumentException()} // change to result
        }
    }

    private fun visitLiteral(node: LiteralNode): Any {
        return node.getValue()
    }

    private fun visitIdentifier(it: IdentifierNode): Any{
        return it.getValue()
    }


    private fun visitCall(node: CallNode) {
        if (node.getValue() == "println"){
            node.getArguments().forEach {
                val value = when (it) {
                    is IdentifierNode -> storage[it.getValue()]
                    is BinaryNode, is LiteralNode -> evaluateNode(it)
                    else -> it.getValue()
                }
                println(value)
            }
        }
        if (node.getValue() == "if"){
            TODO()
        }
        if (node.getValue() == "else"){
            TODO()
        }
        if (node.getValue() == "while"){
            TODO()
        }
        if (node.getValue() == "for"){
            TODO()
        }
    }

    private fun visitAssignment(node: AssignmentNode)  {
        val key = node.getIdentifierNode().getValue()
        val value  = evaluateNode(node.getValueNode())

        storage[key] = value
    }

    fun getStorage(): MutableMap<String, Any> {
        return storage
    }

}
