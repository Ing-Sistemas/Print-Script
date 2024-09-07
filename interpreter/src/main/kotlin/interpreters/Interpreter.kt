package interpreters

import ASTNode
import Expression
import Literal
import Statement
import utils.Storage

class Interpreter {
    private val storage = Storage()

    fun interpret(node: ASTNode): Any {
        return when (node) {
            is Literal -> {
                InterpretLiteral().interpret(node, storage)
            }
            is Expression -> {
                InterpretExpression().interpret(node, storage)
            }
            is Statement -> {
                InterpretStatement().interpret(node, storage)
            }
        }
    }
}