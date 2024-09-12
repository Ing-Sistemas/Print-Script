package org.example

import org.example.semantic.SemanticAnalyzer
import org.example.syntactic.SyntacticAnalyzer
import org.example.syntactic.SyntacticFail
import org.example.syntactic.SyntacticSuccess

/**
 * The Parser class joins both analysers in a single class
 *
 * Takes the hassle of creating an instance for each analyser in the runner because it has its own instances of syntacticAnalyzer and semanticAnalyzer inside the class
 */
class Parser {
    private val syntacticAnalyzer = SyntacticAnalyzer()
    private val semanticAnalyzer = SemanticAnalyzer()

    fun parse(tokens: List<Token>): ASTNode {
        return when (val syntacticResult = syntacticAnalyzer.buildAST(tokens)) {
            is SyntacticFail -> {
                throw Exception("Error: ${syntacticResult.message}")
            }
            is SyntacticSuccess -> { semanticAnalyzer.analyze(syntacticResult.astNode) }
        }
    }
}