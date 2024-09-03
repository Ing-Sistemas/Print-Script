package org.example

class OldInterpreter {
    private val storage = mutableMapOf<String, Any>()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            is AssignmentNode -> visitAssignment(node)
            is CallNode -> visitCall(node)
            is IdentifierNode -> visitIdentifier(node)
            is LiteralNode -> visitLiteral(node)
            is ProgramNode -> visitProgram(node)
            is StatementNode -> visitStatement(node)
            else -> throw IllegalArgumentException("Unknown node type")
//            is TypeDeclarationNode -> TODO("Not necessary")
//            is VariableDeclarationNode -> visitVariableDeclaration(node)
//            is BinaryNode -> evaluateNode(node)
//            is UnaryNode -> TODO("Not necessary")
            // not necessary nodes to evaluate in this context
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

    private fun visitStatement(node: StatementNode) {
        when (val it = node.getStatement()) {
            is AssignmentNode -> visitAssignment(it)
            is CallNode -> visitCall(it)
            is VariableDeclarationNode -> visitVariableDeclaration(it)
            else -> { IllegalArgumentException() }
        }
    }

    private fun visitVariableDeclaration(node: VariableDeclarationNode) {
        val assignmentNode = node.getAssignment()
        val key = assignmentNode.getIdentifierNode().getValue()
        val value = evaluateNode(assignmentNode.getValueNode())
        storage[key] = value
    }

    // think valueChecker should be done better with value token and TokenType

    private fun evaluateNode(node: ASTNode): Any {
        return when (node) {
            is BinaryNode -> {
                val operator = node.getValue()
                val leftValue = evaluateNode(node.getLeft())
                val rightValue = evaluateNode(node.getRight())

                when {
                    leftValue is Int && rightValue is Int -> applyOperator(leftValue, operator, rightValue)
                    leftValue is String && rightValue is String && operator == "+" -> leftValue + rightValue
                    leftValue is String && rightValue is Int && operator == "+" -> leftValue + rightValue.toString()
                    leftValue is Int && rightValue is String && operator == "+" -> leftValue.toString() + rightValue
                    else -> throw IllegalArgumentException("Invalid operands for operator: $operator")
                }
            }
            is LiteralNode -> {
                node.getValue().toIntOrNull() ?: node.getValue()
            }
            is IdentifierNode -> {
                visitIdentifier(node)
            }
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }

    private fun applyOperator(left: Int, operator: String, right: Int): Int {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> { throw IllegalArgumentException() }
        }
    }

    private fun visitLiteral(node: LiteralNode): Any {
        return node.getValue()
    }

    private fun visitIdentifier(node: IdentifierNode): Any {
        return storage[node.getValue()]!!
    }

    private fun visitCall(node: CallNode) {
        if (node.getValue() == "println") {
            node.getArguments().forEach {
                val value = when (it) {
                    is IdentifierNode -> storage[it.getValue()]
                    is BinaryNode, is LiteralNode -> evaluateNode(it)
                    else -> it.getValue()
                }
                println(value)
            }
            return
        }
//        if (node.getValue() == "if") {
//            TODO()
//        }
    }

    private fun visitAssignment(node: AssignmentNode) {
        val key = node.getIdentifierNode().getValue()
        val value = evaluateNode(node.getValueNode())
        storage[key] = value
    }

    fun getStorage(): MutableMap<String, Any> {
        return storage
    }
}