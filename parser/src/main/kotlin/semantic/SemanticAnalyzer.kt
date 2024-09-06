package semantic

import ASTNode
import org.example.*
import org.example.parser.semantic.*
import org.example.parser.semantic.result.ResultFactory

class SemanticAnalyzer {
    private val resultFactory = ResultFactory()
    private val typeCheck = TypeCheck(resultFactory)
    private val storageManager = StorageManager(resultFactory)
    private val operationCheck = OperationCheck(resultFactory)

    fun analyze(node: ASTNode): ASTNode {
        val result = SemanticNodeVisitor(typeCheck, storageManager, operationCheck, resultFactory).visit(node as ProgramNode)
        if (result.getErrors().isNotEmpty()) {
            throw Exception("Semantic error: ${result.getErrors()}")
        }
        return node
    }
}