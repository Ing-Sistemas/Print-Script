package org.example

import org.example.token.TokenType

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
            is BinaryNode -> TODO("Implement")
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
        var key: Any? = null
        var value: Any? = null
        when (val assignmentNode = node.getAssignment()){
            is AssignmentNode -> {
                key = assignmentNode.getIdentifierNode().getValue()
                value = valueChecker(assignmentNode)
            }
            else -> {IllegalArgumentException()}
        }
        storage [key.toString()] = value!!
    }

    //think valueChecker should be done better with value token and TokenType

    private fun valueChecker(value: AssignmentNode): Any {
        val nodeToAnalyze = value.getValueNode()

        if (nodeToAnalyze is BinaryNode){
            val operator = nodeToAnalyze.getValue()
            val left = nodeToAnalyze.getLeft().getValue().toInt()
            val right = nodeToAnalyze.getRight().getValue().toInt()
            val result = applyOperator(left, operator, right)
            return result
        }
        return if (nodeToAnalyze is LiteralNode){
            try {
                nodeToAnalyze.getValue().toInt()
            } catch (e: NumberFormatException) {
                nodeToAnalyze.getValue()
            }
        } else IllegalArgumentException() //change to a result exception "node error"
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
                when (it) {
                    is IdentifierNode -> {
                        println(storage[it.getValue()])
                    }

                    is BinaryNode -> {
                        println(applyOperator(
                            it.getLeft().getValue().toInt(),
                            it.getValue(),
                            it.getRight().getValue().toInt()))
                    }

                    else -> {
                        println(it.getValue())
                    }
                }
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
        val identifier = node.getIdentifierNode()
        val literal  = node.getValueNode()

        storage[identifier.toString()] = literal
    }

    fun getStorage(): MutableMap<String, Any> {
        return storage
    }

}
