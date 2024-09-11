package org.example.parser.syntactic

import ASTNode

sealed interface SyntacticResult

data class SyntacticSuccess(val astNode: ASTNode) : SyntacticResult
data class SyntacticFail(val message: String) : SyntacticResult