package com.printscript.parser.semantic

import com.printscript.*
import com.printscript.ast.ASTNode
import com.printscript.parser.semantic.result.ResultFactory

class SemanticAnalyzer {
    private val resultFactory = ResultFactory()
    private val storageManager = StorageManager(resultFactory)
    private val operationCheck = OperationCheck(resultFactory)

    fun analyze(node: ASTNode): ASTNode {
        val visitor = SemanticNodeVisitor(storageManager, operationCheck, resultFactory)
        val result = node.accept(visitor)
        if (result.getErrors().isNotEmpty()) {
            throw Exception("Semantic error: ${result.getErrors()}")
        }
        return node
    }
}