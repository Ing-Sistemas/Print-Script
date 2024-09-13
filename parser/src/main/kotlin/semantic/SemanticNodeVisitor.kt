package org.example.parser.semantic

import ASTNode
import AssignmentStatement
import BinaryExpression
import BooleanValue
import Expression
import FunctionCallStatement
import IdentifierExpression
import Literal
import NumberLiteral
import NumberValue
import Statement
import StoredValue
import StringLiteral
import StringValue
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Visitor
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation

class SemanticNodeVisitor(
    private val typeCheck: TypeCheck,
    private val storageManager: StorageManager,
    private val operationCheck: OperationCheck,
    private val resultFactory: ResultFactory,
) : Visitor<ResultInformation> {

    override fun visit(expression: Expression): ResultInformation {
        return when (expression) {
            is BinaryExpression -> visit(expression)
            is UnaryExpression -> visit(expression)
            is IdentifierExpression -> visit(expression)
            is TypeDeclarationExpression -> visit(expression)
            is Literal -> visit(expression)
            else -> resultFactory.createError("Unsupported Expression type")
        }
    }

    override fun visit(literal: Literal): ResultInformation {
        return when (literal) {
            is NumberLiteral -> visit(literal)
            is StringLiteral -> visit(literal)
            else -> resultFactory.createError("Unsupported Literal type")
        }
    }

    override fun visit(statement: Statement): ResultInformation {
        return when (statement) {
            is AssignmentStatement -> visit(statement)
            is VariableDeclarationStatement -> visit(statement)
            is FunctionCallStatement -> visit(statement)
            else -> resultFactory.createError("Unsupported Statement type")
        }
    }

    fun visit(node: ASTNode): ResultInformation {
        return when (node) {
            is Expression -> visit(node)
            is Statement -> visit(node)
            else -> resultFactory.createError("Unsupported ASTNode type")
        }
    }

    private fun visit(literalNode: NumberLiteral): ResultInformation {
        return resultFactory.create(NumberValue(literalNode.getValue()), DataType.NUMBER)
    }
    private fun visit(literalNode: StringLiteral): ResultInformation {
        return resultFactory.create(StringValue(literalNode.getValue()), DataType.STRING)
    }

    private fun visit(typeDeclaration: TypeDeclarationExpression): ResultInformation {
        val type = convertToDataType(typeDeclaration.getType())
        val defaultValue = convertToDefaultValues(type)
        return resultFactory.create(defaultValue, type)
    }

    private fun visit(identifierNode: IdentifierExpression): ResultInformation {
        return storageManager.getIdentifierResult(identifierNode)
    }

    private fun visit(variableDeclarationNode: VariableDeclarationStatement): ResultInformation {
        return typeCheck.checkVariableDeclaration(variableDeclarationNode, this)
    }

    private fun visit(binaryNode: BinaryExpression): ResultInformation {
        return operationCheck.checkBinaryOperation(binaryNode, this)
    }

    private fun visit(assignmentNode: AssignmentStatement): ResultInformation {
        return storageManager.handleAssignment(assignmentNode, this)
    }

    private fun visit(unaryNode: UnaryExpression): ResultInformation {
        TODO("ver si tiene un - y q sea number type, ver si es necesario pq no tiene necesidad")
    }

    private fun visit(callNode: FunctionCallStatement): ResultInformation {
        val arguments = callNode.getArguments().map { visit(it) }
        if (arguments.any { it.getErrors().isNotEmpty() }) {
            return resultFactory.createError("Error in function call arguments")
        }
        return resultFactory.create(StringValue(""), DataType.STRING)
        // check if inside the condition there's a bool
    }

    private fun convertToDataType(value: Any): DataType {
        return when (value) {
            is String -> DataType.STRING
            is Double -> DataType.NUMBER
            else -> throw IllegalArgumentException("Unknown data type")
        }
    }

    private fun convertToDefaultValues(type: DataType): StoredValue {
        return when (type) {
            DataType.STRING -> StringValue("")
            DataType.NUMBER -> NumberValue(0.0)
            DataType.BOOLEAN -> BooleanValue(false)
        }
    }
}