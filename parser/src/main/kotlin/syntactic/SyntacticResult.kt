package com.printscript.parser.syntactic

import com.printscript.ast.ASTNode

sealed interface SyntacticResult

data class SyntacticSuccess(val astNode: ASTNode) : SyntacticResult
data class SyntacticFail(val message: String) : SyntacticResult