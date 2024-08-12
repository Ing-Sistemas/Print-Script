import org.example.ASTNode
import org.example.AssignmentNode
import org.example.BinaryNode
import org.example.token.TokenType
import stategy.operators.DivideTokenStrategy

class SemanticAnalyzer {
    fun analyze(node: ASTNode): ASTNode {
        return when (node) {
            //is ProgramNode -> TODO()
            is AssignmentNode -> analyzeAssignment(node)
            is BinaryNode -> analyzeBinary(node)


            else -> throw Exception("Unknown node type: ${node.javaClass}")
        }
    }

    private fun analyzeAssignment(node: AssignmentNode): ASTNode {
        val identifier = node.getIdentifierNode()
        val literal = node.getLiteralNode()

        compareTypes(identifier.getToken(), literal.getToken())
        return node
    }

    private fun analyzeBinary(node: BinaryNode): ASTNode {
        val left = node.getLeft()
        val right = node.getRight()

        compareTypes(left.getToken(), right.getToken())
        checkOperator(node.getToken())
        return node
    }

    private fun checkOperator(operator: Token) {
        if (operator.getValue() != "+" &&
            operator.getValue() != "-" &&
            operator.getValue() != "*" &&
            operator.getValue() != "/"
        ) {
            throw Exception("Invalid operator: ${operator.getValue()}")
        }
    }




    private fun compareTypes(left: Token, right: Token) {
        if (left.getType() != right.getType()) {
            throw Exception("Type mismatch: ${left.getType()} != ${right.getType()}")
        }
    }


}