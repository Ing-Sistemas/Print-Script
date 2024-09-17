package interpreters

import AssignmentStatement
import BooleanValue
import EmptyVarDeclarationStatement
import FunctionCallStatement
import IfStatement
import NumberValue
import Statement
import StoredValue
import StringValue
import VariableDeclarationStatement
import org.example.output.Printer
import utils.Storage

class InterpretStatement {

    fun interpret(node: Statement, storage: Storage): Any {
        return when (node) {
            is VariableDeclarationStatement -> {
                val declarator = node.getDeclarator()
                val value = node.getAssignmentExpression()?.getValue()
                storage.addToStorage(declarator, convertToStoredValue(InterpretExpression().interpret(value!!, storage)))
            }

            is FunctionCallStatement -> {
                val functionName = node.getFunctionName()
                val arguments = node.getArguments()
                when (functionName) {
                    "println" -> {
                        for (argument in arguments) {
                            Printer().output(InterpretExpression().interpret(argument, storage))
                        }
                    }

                    "if" -> {
                        if (arguments.size != 1) {
                            throw IllegalArgumentException("If statement requires one condition argument.")
                        }
                        val condition = InterpretExpression().interpret(arguments[0], storage) as Boolean

                        if (condition) {
                            val body = node.getBody()
                            body?.forEach { astNode ->
                                Interpreter().interpret(astNode, storage)
                            }
                        } else {
                            interpret(node, storage) // here it should continue but IDK how tho
                        }
                    }
                    "else" -> {
                        val body = node.getBody()
                        body?.forEach { astNode ->
                            Interpreter().interpret(astNode, storage)
                        }
                    }

                    else -> {
                        throw (IllegalArgumentException("Function $functionName is not defined"))
                    }
                }
            }

            is AssignmentStatement -> {
                val identifier = node.getIdentifier().getIdentifier()
                val value = InterpretExpression().interpret(node.getValue(), storage)
                val valueToUse = convertToStoredValue(value)
                return storage.addToStorage(identifier, valueToUse)
            }

            is EmptyVarDeclarationStatement -> {
                val declarator = node.getDeclarator()
                storage.addToStorage(declarator, null)
            }

            is IfStatement -> TODO()
        }!!
    }

    private fun convertToStoredValue(value: Any): StoredValue {
        return when (value) {
            is Double -> NumberValue(value)
            is String -> StringValue(value)
            is Boolean -> BooleanValue(value)
            else -> throw IllegalArgumentException("Invalid value type")
        }
    }
}