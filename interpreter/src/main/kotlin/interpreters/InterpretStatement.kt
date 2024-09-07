package interpreters

import AssignmentStatement
import FunctionCallStatement
import Statement
import VariableDeclarationStatement
import org.example.output.Printer
import output.Conditional
import utils.Storage

class InterpretStatement {

    fun interpret(node: Statement, storage: Storage): Any {
        return when (node) {
            is VariableDeclarationStatement -> {
                val declarator = node.getDeclarator()
                val value = node.getAssignmentExpression()?.getValue()
                storage.addToStorage(declarator, InterpretExpression().interpret(value!!, storage))
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
                        for (argument in arguments) {
                            Conditional().output(InterpretExpression().interpret(argument, storage))
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
                return storage.addToStorage(identifier, value)
            }
        }
    }
}