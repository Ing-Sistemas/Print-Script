package interpreters

import AssignmentStatement
import FunctionCallStatement
import Statement
import VariableDeclarationStatement
import org.example.output.Printer
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
                if (functionName == "println") {
                    for (argument in arguments) {
                        Printer().output(InterpretExpression().interpret(argument, storage))
                    }
                } else {
                    throw (IllegalArgumentException("Function $functionName is not defined"))
                }
            }
            is AssignmentStatement -> {
                val identifier = node.getIdentifier()
                val saveIdentifier = InterpretExpression().interpret(identifier, storage)
                val value = InterpretExpression().interpret(node.getValue(), storage)
                return storage.addToStorage(saveIdentifier.toString(), value)
            }
        }
    }
}