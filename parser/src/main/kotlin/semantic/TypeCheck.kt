package org.example.parser.semantic

import BooleanValue
import EmptyValue
import NumberValue
import StoredValue
import StringValue
import VariableDeclarationStatement
import org.example.parser.semantic.result.ResultFactory
import org.example.parser.semantic.result.ResultInformation

class TypeCheck(private val resultFactory: ResultFactory) {

    fun checkVariableDeclaration(
        node: VariableDeclarationStatement,
        visitor: VisitorSemantic<ResultInformation>,
    ): ResultInformation {
        val declarator = node.getDeclarator()
        val mutability = if (declarator == "const") false else true

        val assignmentResult = node.getAssignmentExpression().accept(visitor)

        // todo if vardec.declarator is "const" construct is as immutable
        // todo then check in the assignment if the isMutable for allowance to change
        // if (node.getDeclarator() == "const") llamar a una funcion cree un StoreValue inmutable
        // hacerlo en un metodo distinto q llame directo a StorageManager.handleAssignment
        // ver si esto hay q hacerlo antes de definir assignmentResult
        // sino ver como pasarle q tiene q ser immutable a la hora de crearlo, de alguna forma
        // le tiene q llegar hasta el StorageManager.handleAssignment
        if (assignmentResult.getErrors().isNotEmpty()) return assignmentResult

        val declarationNodeType = node.getTypeDeclarationExpression().getType()

        return if (declarationNodeType != assignmentResult.getType().toString()) {
            resultFactory.createError("Type mismatch for var dec")
        } else {
            resultFactory.create(convertToStoredValue(assignmentResult), assignmentResult.getType(), assignmentResult.getMutability())
        }
    }

    private fun convertToStoredValue(result: ResultInformation): StoredValue {
        return when (result.getType()) {
            DataType.STRING -> StringValue(result.getValue(), result.getMutability())
            DataType.NUMBER -> NumberValue(result.getValue(), result.getMutability())
            DataType.BOOLEAN -> BooleanValue(result.getValue(), result.getMutability())
            DataType.NULL -> EmptyValue(result.getValue(), result.getMutability())
        }
    }
}