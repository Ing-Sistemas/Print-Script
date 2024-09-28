package com.printscript.parser.semantic

import com.printscript.ast.*
import com.printscript.parser.semantic.result.ResultFactory
import com.printscript.parser.semantic.result.ResultInformation

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

    override fun visit(ifStatement: IfStatement): ResultInformation {
        val condition = ifStatement.getCondition()
        val thenStatement = ifStatement.getThenStatement()
        val elseStatement = ifStatement.getElseStatement()

        if (condition is BooleanLiteral) {
            thenStatement.forEach { it.accept(this) }
            elseStatement?.forEach { it.accept(this) }
            return resultFactory.create(StringValue(""), DataType.STRING)
        } else {
            return resultFactory.createError("Condition is not Boolean")
        }
    }

    override fun visit(readInputNode: ReadInputNode): ResultInformation {
        return resultFactory.create(StringValue(""), DataType.STRING) // TODO check
    }

    override fun visit(readEnvNode: ReadEnvNode): ResultInformation {
        return resultFactory.create(StringValue(""), DataType.STRING)
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
        if (unaryExpression.getOperator() != "-") {
            resultFactory.createError("Invalid unary operator: ${unaryExpression.getOperator()}")
        }
        return unaryExpression.getRight().accept(this)
    }

    override fun visit(functionCallStatement: FunctionCallStatement): ResultInformation {
        val arguments = functionCallStatement.getBody()?.map { it.accept(this) }
        if (arguments != null) {
            if (arguments.any { it.getErrors().isNotEmpty() }) {
                return resultFactory.createError("Error in function call body")
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