package interpreters

import ASTNode
import Expression
import Literal
import Statement
import utils.Storage

class Interpreter {

    fun interpret(node: ASTNode, storage: Storage): Any {
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