package interpreters

import AssignmentStatement
import EmptyVarDeclarationStatement
import FunctionCallStatement
import Statement
import StoredValue
import VariableDeclarationStatement
import org.example.output.Printer
import utils.Storage

class InterpretStatement {

    fun interpret(node: Statement, storage: Storage): Any {
        return when (node) {
            is VariableDeclarationStatement -> {
                val declarator = node.getDeclarator()
                val value = node.getAssignmentExpression()?.getValue()
                storage.addToStorage(declarator, InterpretExpression().interpret(value!!, storage) as StoredValue)
            }

            is FunctionCallStatement -> {
                val functionName = node.getFunctionName()
                val arguments = node.getArguments()
                val body = node.getBody()
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
                            body?.forEach { astNode ->
                                Interpreter().interpret(astNode, storage)
                            }
                        } else {
                            println("Condition is false, skipping body.")
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
                return storage.addToStorage(identifier, value as StoredValue)
            }

            is EmptyVarDeclarationStatement -> TODO("guardar la variable completa")
        }!!
    }
}