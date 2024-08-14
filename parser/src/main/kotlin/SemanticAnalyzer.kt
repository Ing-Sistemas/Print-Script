import org.example.*


class SemanticAnalyzer {
    fun analyze(node: ASTNode): ASTNode {
        return when (node) {
            is ProgramNode -> analyzeProgramNode(node.getChildren())
            is AssignmentNode -> analyzeAssignment(node)
            is BinaryNode -> analyzeBinary(node)
            is CallNode -> analyzeCall(node)
            is IdentifierNode -> node
            is LiteralNode -> node
            is TypeDeclarationNode -> node
            is UnaryNode -> node //TODO("check whats for")
            is VariableDeclarationNode -> analyzeVarDec(node)
            is StatementNode -> node //TODO()
        }
    }

    private fun analyzeProgramNode(children: List<ASTNode>): ASTNode {
        children.forEach { analyze(it) }
        return ProgramNode(0, 0, children) //TODO("tf el start y end)
    }

    private fun analyzeAssignment(node: AssignmentNode): ASTNode {
        val identifier = node.getIdentifierNode()
        val literal = node.getValueNode()

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

    private fun analyzeCall(node: CallNode): ASTNode {
        if (node.getArguments().isEmpty()) {
            throw Exception("Call node must have arguments")
        }
        return node
    }

    private fun analyzeVarDec(node: VariableDeclarationNode): ASTNode {
        analyze(node.getTypeDeclaration() as TypeDeclarationNode)
        analyzeAssignment(node.getAssignment() as AssignmentNode) //TODO("decidir")
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