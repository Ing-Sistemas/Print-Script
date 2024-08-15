package org.example

import org.example.token.TokenType

class NodeVisitor: Visitor<Any> {
    private val storage: MutableMap<String, Any> = mutableMapOf()
    //TODO("all exception -> Result")

    override fun visit(identifierNode: IdentifierNode): Any {
        return storage[identifierNode.getToken().getValue()] ?: throw Exception("Variable not found")
    }

    override fun visit(literalNode: LiteralNode): Any {
        return literalNode.getValue()
    }

    override fun visit(typeDeclaration: TypeDeclarationNode): Any {
        return typeDeclaration.getValue()
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): Any {
        val typeDeclaration = variableDeclarationNode.getTypeDeclaration().accept(this)
        val assigmentValue = variableDeclarationNode.getAssignment().accept(this)
        storage[variableDeclarationNode.getToken().getValue()] = assigmentValue
        return assigmentValue
    }

    override fun visit(programNode: ProgramNode): Any {
        val results = mutableListOf<Any>()
        programNode.getChildren().forEach {
            val result = it.accept(this)
            results.add(result)
        }
        return results
    }

    override fun visit(binaryNode: BinaryNode): Any {
        val left = binaryNode.getLeft().accept(this)
        val right = binaryNode.getRight().accept(this)
        return when (binaryNode.getToken().getType()) {
            TokenType.PLUS_OPERATOR -> left as Int + right as Int
            TokenType.MINUS_OPERATOR -> left as Int - right as Int
            TokenType.MULTIPLY_OPERATOR -> left as Int * right as Int
            TokenType.DIVIDE_OPERATOR -> left as Int / right as Int
            else -> throw Exception("Invalid operation")
        }
    }

    override fun visit(assignmentNode: AssignmentNode): Any {
        val value = assignmentNode.getValueNode().accept(this)
        val identifier = assignmentNode.getIdentifierNode().getToken().getValue()
        storage[identifier] = value
        return value
    }

    override fun visit(statementNode: StatementNode): Any {
        return statementNode.getStatement().accept(this)
    }

    override fun visit(unaryNode: UnaryNode): Any {
        TODO("Not yet implemented")
    }

    override fun visit(callNode: CallNode): Any {
        TODO("Not yet implemented")
    }
}