package org.example.parser.semantic.validate

import org.example.*
import org.example.parser.semantic.ResultInformation

class SemanticNodeVisitor: Visitor<ResultInformation> {
    //TODO("all exception -> Result")
    private val storage = mutableMapOf<String, Any>()

    override fun visit(programNode: ProgramNode): ResultInformation {
        val errors = mutableListOf<String>()
        programNode.getChildren().forEach {
            val result = it.accept(this)
            result.getErrors().forEach { errors.add(it) }
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
        if (variableName in storage) return ResultInformation(variableName, null, listOf("Variable name already in use"))
        return ResultInformation(variableName, null, emptyList())
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): ResultInformation {
        val typeDeclaration = variableDeclarationNode.getTypeDeclaration().getValue() //'string' o 'number'
        val assignmentValue = variableDeclarationNode.getAssignment().accept(this)
        if (assignmentValue.getErrors().isNotEmpty()) return ResultInformation(null, null, assignmentValue.getErrors())
        val typeMatchingAssignmentSide = if (assignmentValue.getType() == "NUMBER_TYPE") "number" else "string"
        if (typeDeclaration != typeMatchingAssignmentSide) return ResultInformation(null, null, listOf("Type mismatch for variable declaration"))
        return ResultInformation(assignmentValue.getValue(), assignmentValue.getType(), emptyList())
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
                    ResultInformation(null, null, listOf("Type mismatch for subtraction"))
                }
            }
            "/" -> {
                if (left.getType() == right.getType()) {
                    val operation = left.getValue()!!.toInt() / right.getValue()!!.toInt()
                    ResultInformation(operation.toString(), left.getType(), emptyList())
                } else {
                    ResultInformation(null, null, listOf("Type mismatch for division"))
                }
            }
            "*" -> {
                if (left.getType() == right.getType()) {
                    val operation = left.getValue()!!.toInt() * right.getValue()!!.toInt()
                    ResultInformation(operation.toString(), left.getType(), emptyList())
                } else {
                    ResultInformation(null, null, listOf("Type mismatch for multiplication"))
                }
            }
            else -> ResultInformation(null, null, listOf("Invalid operator specified"))
        }
    }

    override fun visit(assignmentNode: AssignmentNode): ResultInformation {
        val identifierNodeName = assignmentNode.getIdentifierNode().accept(this)
        val valueNode = assignmentNode.getValueNode().accept(this)
        val value = tryToInt(valueNode.getValue().toString())
        //agregar un try para convertir a int el valueNode
        storage[identifierNodeName.getValue().toString()] = value
        return ResultInformation(valueNode.getValue(), valueNode.getType(), identifierNodeName.getErrors() + valueNode.getErrors())
    }

    private fun tryToInt(value: String): Any {
        return try {
            value.toInt()
        } catch (e: Exception) {
            value
        }
    }

    override fun visit(statementNode: StatementNode): ResultInformation {
        return statementNode.getStatement().accept(this)
    }

    override fun visit(unaryNode: UnaryNode): ResultInformation {
        TODO("Not yet implemented")
    }

    override fun visit(callNode: CallNode): ResultInformation {
        return ResultInformation(null, null, emptyList())
    }
}