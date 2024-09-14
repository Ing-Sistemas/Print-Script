package org.example.parser.semantic

import AssignmentStatement
import BinaryExpression
import BooleanLiteral
import BooleanValue
import EmptyValue
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
    private val typeCheck: TypeCheck,
    private val storageManager: StorageManager,
    private val operationCheck: OperationCheck,
    private val resultFactory: ResultFactory,
) : VisitorSemantic<ResultInformation> {

    override fun visit(numberLiteral: NumberLiteral, isMutable: Boolean): ResultInformation {
        return resultFactory.create(NumberValue(numberLiteral.getValue(), isMutable), DataType.NUMBER, isMutable)
    }
    override fun visit(stringLiteral: StringLiteral, isMutable: Boolean): ResultInformation {
        return resultFactory.create(StringValue(stringLiteral.getValue(), isMutable), DataType.STRING, isMutable)
    }

    override fun visit(booleanLiteral: BooleanLiteral, isMutable: Boolean): ResultInformation {
        return resultFactory.create(BooleanValue(booleanLiteral.getValue(), isMutable), DataType.BOOLEAN, isMutable)
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
        return typeCheck.checkVariableDeclaration(variableDeclarationStatement, this)
    }

    override fun visit(emptyVarDeclarationStatement: EmptyVarDeclarationStatement): ResultInformation {
        if (emptyVarDeclarationStatement.getDeclarator() == "const") {
        }
        return resultFactory.createError("To implement emptyVarDeclarationStatement")
    }

    override fun visit(binaryExpression: BinaryExpression): ResultInformation {
        return operationCheck.checkBinaryOperation(binaryExpression, this)
    }

    override fun visit(assignmentStatement: AssignmentStatement, isMutable: Boolean): ResultInformation {
        return storageManager.handleAssignment(assignmentStatement, isMutable, this)
    }

    override fun visit(unaryExpression: UnaryExpression): ResultInformation {
        TODO("ver si tiene un - y q sea number type, ver si es necesario pq no tiene necesidad")
    }

    override fun visit(functionCallStatement: FunctionCallStatement): ResultInformation {
        val arguments = functionCallStatement.getArguments().map { it.accept(this) }
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
            DataType.NULL -> EmptyValue(null)
        }
    }
}