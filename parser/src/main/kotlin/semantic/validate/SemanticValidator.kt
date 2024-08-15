package org.example.parser.semantic.validate

import org.example.ASTNode

interface SemanticValidator {
    fun validate(nodeList: List<ASTNode>): Boolean
    //TODO("cambiar de Bool a Result")
}