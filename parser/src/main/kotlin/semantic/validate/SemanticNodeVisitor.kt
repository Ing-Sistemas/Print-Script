package org.example.parser.semantic.validate

import org.example.*
import org.example.parser.semantic.ResultInformation

class SemanticNodeVisitor: Visitor<ResultInformation> {
    //TODO("all exception -> Result")
    private val storage = mutableMapOf<String, String>()

    override fun visit(programNode: ProgramNode): ResultInformation {
        val errors = mutableListOf<Any>()
        programNode.getChildren().forEach {
            val result = it.accept(this)
            errors.add(result)
        }
        return ResultInformation(null, null, errors)
    }

    override fun visit(literalNode: LiteralNode): ResultInformation {
        return ResultInformation(literalNode.getValue(), literalNode.getType(), emptyList())
    }

    override fun visit(typeDeclaration: TypeDeclarationNode): ResultInformation {
        return ResultInformation(null, typeDeclaration.getValue(), emptyList())
    }

    override fun visit(identifierNode: IdentifierNode): ResultInformation {
        val variableName = identifierNode.getValue()
        if (variableName in storage) return ResultInformation(variableName, null, listOf(Error("Variable name already in use")))
        return ResultInformation(variableName, null, emptyList())
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): ResultInformation {
        val typeDeclaration = variableDeclarationNode.getTypeDeclaration().getValue() //'string' o 'number'
        val assigmentValue = variableDeclarationNode.getAssignment().accept(this)
        if (assigmentValue.getErrors().isNotEmpty()) return ResultInformation(null, null, assigmentValue.getErrors())
        if (typeDeclaration != assigmentValue.getType()) return ResultInformation(null, null, listOf(Error("Type mismatch")))
        return ResultInformation(assigmentValue.getValue(), assigmentValue.getType(), emptyList())
    }

    override fun visit(binaryNode: BinaryNode): ResultInformation {
        val left = binaryNode.getLeft().accept(this)
        val right = binaryNode.getRight().accept(this)
        return performOperation(left, right, binaryNode.getValue())
    }

    private fun performOperation(left: ResultInformation, right: ResultInformation, operator: String): ResultInformation {
        return when (operator) {
            "+" -> ResultInformation(left.getValue() + right.getValue(), null, emptyList())
            "-" -> {
                if (left.getType() == right.getType()) {
                    val operation = left.getValue()!!.toInt() - right.getValue()!!.toInt()
                    ResultInformation(operation.toString(), left.getType(), emptyList())
                } else {
                    ResultInformation(null, null, listOf(Error("Type mismatch")))
                }
            }
            "/" -> {
                if (left.getType() == right.getType()) {
                    val operation = left.getValue()!!.toInt() / right.getValue()!!.toInt()
                    ResultInformation(operation.toString(), left.getType(), emptyList())
                } else {
                    ResultInformation(null, null, listOf(Error("Type mismatch")))
                }
            }
            "*" -> {
                if (left.getType() == right.getType()) {
                    val operation = left.getValue()!!.toInt() * right.getValue()!!.toInt()
                    ResultInformation(operation.toString(), left.getType(), emptyList())
                } else {
                    ResultInformation(null, null, listOf(Error("Type mismatch")))
                }
            }
            else -> ResultInformation(null, null, listOf(Error("Invalid operator specified")))
        }
    }

    override fun visit(assignmentNode: AssignmentNode): ResultInformation {
        val identifierNodeName = assignmentNode.getIdentifierNode().accept(this)
        val valueNode = assignmentNode.getValueNode().accept(this)
        storage[identifierNodeName.getValue().toString()] = valueNode.getValue().toString()
        return ResultInformation(valueNode.getValue(), valueNode.getType(), identifierNodeName.getErrors() + valueNode.getErrors())
    }

    override fun visit(statementNode: StatementNode): ResultInformation {
        return statementNode.getStatement().accept(this)
    }

    override fun visit(unaryNode: UnaryNode): ResultInformation {
        TODO("Not yet implemented")
    }

    override fun visit(callNode: CallNode): ResultInformation {
        TODO("Not yet implemented")
    }
}