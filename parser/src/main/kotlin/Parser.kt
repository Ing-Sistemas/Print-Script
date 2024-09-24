package org.example.parser

import ASTNode
import Token
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticFail
import org.example.parser.syntactic.SyntacticSuccess
import semantic.SemanticAnalyzer

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