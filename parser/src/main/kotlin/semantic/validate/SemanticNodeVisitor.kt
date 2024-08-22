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
        if (variableName in storage) {
            val value = storage[variableName].toString()
            val type = storage[variableName]?.let { getTypeForVar(it) }
            return ResultInformation(variableName, type, emptyList())
        }
        return ResultInformation(variableName, null, emptyList())
    }

    private fun getTypeForVar(value: Any): String {
        return when (value) {
            is Int -> "LITERAL_NUMBER"
            is String -> "LITERAL_STRING"
            else -> "UNKNOWN"
        }
    }

    override fun visit(variableDeclarationNode: VariableDeclarationNode): ResultInformation {
        val typeDeclaration = variableDeclarationNode.getTypeDeclaration().getValue() //'string' o 'number'
        val identifierName = variableDeclarationNode.getAssignment().getIdentifierNode().getValue()
        if (identifierName in storage) return ResultInformation(null, null, listOf("Variable name already in use"))
        val assignmentValue = variableDeclarationNode.getAssignment().accept(this)
        if (assignmentValue.getErrors().isNotEmpty()) return ResultInformation(null, null, assignmentValue.getErrors())
        val typeMatchingAssignmentSide = if (assignmentValue.getType() == "LITERAL_NUMBER") "number" else "string"
        if (typeDeclaration != typeMatchingAssignmentSide) return ResultInformation(null, null, listOf("Type mismatch for variable declaration"))
        return ResultInformation(assignmentValue.getValue(), assignmentValue.getType(), emptyList())
    }

    override fun visit(binaryNode: BinaryNode): ResultInformation {
        val left = binaryNode.getLeft().accept(this)
        val right = binaryNode.getRight().accept(this)
        return performOperation(left, right, binaryNode.getValue())
    }

    private fun performOperation(left: ResultInformation, right: ResultInformation, operator: String): ResultInformation {
        val leftValue = getIntValue(left.getValue().toString(), storage)
        val rightValue = getIntValue(right.getValue().toString(), storage)
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
                    val operation = leftValue!! / rightValue!!
                    ResultInformation(operation.toString(), "LITERAL_NUMBER", emptyList())
                } else {
                    ResultInformation(null, null, listOf("Type mismatch for division"))
                }
            }
            "*" -> {
                if (left.getType() == right.getType()) {
                    val operation = leftValue!! * rightValue!!
                    ResultInformation(operation.toString(), "LITERAL_NUMBER", emptyList())
                } else {
                    ResultInformation(null, null, listOf("Type mismatch for multiplication"))
                }
            }
            else -> ResultInformation(null, null, listOf("Invalid operator specified"))
        }
    }

    private fun getIntValue(key: String, map: Map<String, Any>): Int? {
        val value = map[key]
        return when (value) {
            is Int -> value
            is String -> value.toIntOrNull() // Convert String to Int if possible
            else -> null // Return null if it's neither an Int nor a convertible String
        }
    }

    override fun visit(assignmentNode: AssignmentNode): ResultInformation {
        val identifierNodeName = assignmentNode.getIdentifierNode().accept(this)
        val valueNode = assignmentNode.getValueNode().accept(this)
//        if (storage[valueNode] != null) {
//            return ResultInformation(storage[valueNode], valueNode.getType(), emptyList())
//        }
        // todo ver si el valueNode esta como key en storage.
        val value = tryToInt(valueNode.getValue().toString())
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