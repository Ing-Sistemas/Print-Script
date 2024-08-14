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
                key = assignmentNode.getIdentifierNode().getToken().getValue()
                value = valueChecker(assignmentNode.getValueNode().getToken().getValue())
            }
            else -> {IllegalArgumentException()}
        }
        storage [key.toString()] = value!!
    }

    //think valueChecker should be done better with value token and TokenType

    private fun valueChecker(value: String): Any {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            value
        }
    }

    private fun visitLiteral(node: LiteralNode): Any {
        return node.getToken().getValue()
    }

    private fun visitIdentifier(it: IdentifierNode): Any{
        return it.getToken().getValue()
    }


    private fun visitCall(node: CallNode) {
        if (node.getToken().getValue() == "println"){
            node.getArguments().forEach {
                if (it is IdentifierNode){
                    println(storage[it.getToken().getValue()])
                }
                else {
                    println(it.getToken().getValue())
                }
            }
        }
        if (node.getToken().getValue() == "if"){
            TODO()
        }
        if (node.getToken().getValue() == "else"){
            TODO()
        }
        if (node.getToken().getValue() == "while"){
            TODO()
        }
        if (node.getToken().getValue() == "for"){
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