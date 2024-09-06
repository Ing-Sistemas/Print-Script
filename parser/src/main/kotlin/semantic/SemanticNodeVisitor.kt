package org.example.parser.semantic

import AssignmentStatement
import BinaryExpression
import Expression
import FunctionCallStatement
import IdentifierExpression
import Literal
import NumberLiteral
import Statement
import StringLiteral
import TypeDeclarationExpression
import UnaryExpression
import VariableDeclarationStatement
import Visitor
import org.example.*
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation

class SemanticNodeVisitor(
    private val typeCheck: TypeCheck,
    private val storageManager: StorageManager,
    private val operationCheck: OperationCheck,
    private val resultFactory: ResultFactory,
) : Visitor<ResultInformation> {
    override fun visit(expression: Expression): ResultInformation {
        TODO("Not yet implemented")
    }

    override fun visit(literal: Literal): ResultInformation {
        TODO("Not yet implemented")
    }

    override fun visit(statement: Statement): ResultInformation {
        TODO("Not yet implemented")
    }

    fun visit(literalNode: NumberLiteral): ResultInformation {
        return resultFactory.create(literalNode.getValue(), DataType.NUMBER)
    }
    fun visit(literalNode: StringLiteral): ResultInformation {
        return resultFactory.create(literalNode.getValue(), DataType.STRING)
    }

    fun visit(typeDeclaration: TypeDeclarationExpression): ResultInformation {
        val type = convertToDataType(typeDeclaration.getType())
        val defaultValue = convertToDefaultValues(type)
        return resultFactory.create(defaultValue, type)
    }

    fun visit(identifierNode: IdentifierExpression): ResultInformation {
        return storageManager.getIdentifierResult(identifierNode)
    }

    fun visit(variableDeclarationNode: VariableDeclarationStatement): ResultInformation {
        return typeCheck.checkVariableDeclaration(variableDeclarationNode, this)
    }

    fun visit(binaryNode: BinaryExpression): ResultInformation {
        return operationCheck.checkBinaryOperation(binaryNode, this)
    }

    fun visit(assignmentNode: AssignmentStatement): ResultInformation {
        return storageManager.handleAssignment(assignmentNode, this)
    }

    fun visit(unaryNode: UnaryExpression): ResultInformation {
        TODO("Not yet implemented")
    }

    fun visit(callNode: FunctionCallStatement): ResultInformation {
        return resultFactory.create(null, null)
        //visit de la lista de Expression
    }

    private fun convertToDataType(value: Any): DataType {
        return when (value) {
            is String -> DataType.STRING
            is Double -> DataType.NUMBER
            else -> throw IllegalArgumentException("Unknown data type")
        }
    }

    private fun convertToDefaultValues(type: DataType): Any {
        return when (type) {
            DataType.STRING -> ""
            DataType.NUMBER -> 0.0
        }
    }
}