package com.printscript.parser

import com.printscript.ast.ASTNode
import com.printscript.parser.semantic.SemanticAnalyzer
import com.printscript.parser.syntactic.SyntacticAnalyzer
import com.printscript.parser.syntactic.SyntacticFail
import com.printscript.parser.syntactic.SyntacticSuccess
import com.printscript.token.Token

class Parser {
    private val syntacticAnalyzer = SyntacticAnalyzer()
    private val semanticAnalyzer = SemanticAnalyzer()

    fun parse(tokens: Iterator<Token>): ASTNode {
        return when (val syntacticResult = syntacticAnalyzer.build(tokens)) {
            is SyntacticFail -> {
                throw Exception("Error: ${syntacticResult.message}")
            }
            is SyntacticSuccess -> { semanticAnalyzer.analyze(syntacticResult.astNode) }
        }
    }
}