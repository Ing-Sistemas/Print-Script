package org.example.parser.semantic

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import BooleanValue
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IdentifierExpression
import NumberLiteral
import NumberValue
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
    private val storageManager: StorageManager,
    private val operationCheck: OperationCheck,
    private val resultFactory: ResultFactory,
) : Visitor<ResultInformation> {

    override fun visit(numberLiteral: NumberLiteral): ResultInformation {
        return resultFactory.create(NumberValue(numberLiteral.getValue()), DataType.NUMBER)
    }

    override fun visit(stringLiteral: StringLiteral): ResultInformation {
        return resultFactory.create(StringValue(stringLiteral.getValue()), DataType.STRING)
    }

    override fun visit(booleanLiteral: BooleanLiteral): ResultInformation {
        return resultFactory.create(BooleanValue(booleanLiteral.getValue()), DataType.BOOLEAN)
    }

    override fun visit(typeDeclarationExpression: TypeDeclarationExpression): ResultInformation {
        val type = convertToDataType(typeDeclarationExpression.getType())
        val defaultValue = convertToDefaultValues(type)
        return resultFactory.create(defaultValue, type)
    }

    override fun visit(identifierExpression: IdentifierExpression): ResultInformation {
        return storageManager.getIdentifierResult(identifierExpression)
    }

    override fun visit(variableDeclarationStatement: VariableDeclarationStatement): ResultInformation {
        return storageManager.handleVariableDeclaration(variableDeclarationStatement, this)
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement): ResultInformation {
        return storageManager.handleEmptyVariableDeclaration(emptyVarDeclarationStatement, this)
    }

    override fun visit(binaryExpression: BinaryExpression): ResultInformation {
        return operationCheck.checkBinaryOperation(binaryExpression, this)
    }

    override fun visit(assignmentStatement: AssignmentStatement): ResultInformation {
        return storageManager.handleAssignment(assignmentStatement, this)
    }

    override fun visit(unaryExpression: UnaryExpression): ResultInformation {
        TODO("ver si tiene un - y q sea number type, ver si es necesario pq no tiene necesidad")
    }

    override fun visit(functionCallStatement: FunctionCallStatement): ResultInformation {
        if (functionCallStatement.getFunctionName() == "if") {
            val args = functionCallStatement.getArguments()
            if (args.all { it is BooleanLiteral }) {
                return resultFactory.create(StringValue(""), DataType.STRING)
            } else {
                return resultFactory.createError("Not all arguments are BooleanLiteral")
            }
        } else { // meaning it's a println (atm)
            val arguments = functionCallStatement.getBody()?.map { it.accept(this) }
            if (arguments != null) {
                if (arguments.any { it.getErrors().isNotEmpty() }) {
                    return resultFactory.createError("Error in function call body")
                }
            }
        }
        return resultFactory.create(StringValue(""), DataType.STRING)
    }

    private fun convertToDataType(value: Any): DataType {
        return when (value) {
            is String -> DataType.STRING
            is Double -> DataType.NUMBER
            is Boolean -> DataType.BOOLEAN
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