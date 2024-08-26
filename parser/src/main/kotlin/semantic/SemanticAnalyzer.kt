package semantic

import org.example.*
import org.example.parser.semantic.validate.SemanticNodeVisitor


class SemanticAnalyzer {
    fun analyze(node: ASTNode): ASTNode {
        val result = SemanticNodeVisitor().visit(node as ProgramNode)
        if (result.getErrors().isNotEmpty()) {
            throw Exception("Semantic error: ${result.getErrors()}")
        }
        return node
    }
}