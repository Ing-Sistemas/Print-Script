package interpreters

import Literal
import NumberLiteral
import StringLiteral
import utils.Storage

class InterpretLiteral {

    fun interpret(node: Literal, storage: Storage): Any {
        return when (node) {
            is NumberLiteral -> {
                return node.getValue()
            }
            is StringLiteral -> {
                return node.getValue()
            }
        }
    }
}