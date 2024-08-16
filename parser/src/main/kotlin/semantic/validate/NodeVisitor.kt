package org.example.parser.semantic.validate

import org.example.*

class NodeVisitor: Visitor<Any> {
    //TODO("all exception -> Result")
    val storage = mutableMapOf<String, String>()

    override fun visit(programNode: ProgramNode): Any {
        val errors = mutableListOf<Any>()
        programNode.getChildren().forEach {
            val result = it.accept(this)
            errors.add(result)
        }
        return errors
    }

    override fun visit(literalNode: LiteralNode): Any {
        return literalNode.getType()
    }

    override fun visit(typeDeclaration: TypeDeclarationNode): Any {
        return typeDeclaration.getValue()
    }

    override fun visit(identifierNode: IdentifierNode): Any {
        TODO("Existe o no, aka, ese nombre ya esta usado")
        TODO("hacerlo ungadunga con ifs")
        TODO("hago el chequeo necesario")
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): Any {
        val typeDeclaration = variableDeclarationNode.getTypeDeclaration().getValue() //'string' o 'number'
        val assigmentValue = variableDeclarationNode.getAssignment().accept(this)
        return typeDeclaration == assigmentValue.getType() //el type del result de la variable
    }

    override fun visit(binaryNode: BinaryNode): Any {
        val left = binaryNode.getLeft().accept(this)
        val right = binaryNode.getRight().accept(this)
        return when (binaryNode.getValue()) {
            is "+" -> Result(sucess, getType = string)
            is "-" -> {
                if (left.getType() == right.getType()) ?: Result(sucess, getType)
            }
            is "/" -> {
                if (left.getType() == right.getType()) ?: Result(sucess, getType)
            }
            is "*" -> {
                if (left.getType() == right.getType()) ?: Result(sucess, getType)
            }
            else -> Result(error)
        }
        //TODO(extract into function)
    }

    override fun visit(assignmentNode: AssignmentNode): Any {
        val identifierNodeName = assignmentNode.getIdentifierNode().accept(this)
        if (identifierNodeName in storage) return Error("name already in use")
        val valueNode = assignmentNode.getValueNode().accept(this)
        return Result(identifier, valueNode.getType)
        //el get type del value node sale del result
        // ambos el identifier y el value node devuelven un Result
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