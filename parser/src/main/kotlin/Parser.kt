package org.example.parser

import ASTNode
import Token
import org.example.parser.syntactic.SyntacticAnalyzer
import org.example.parser.syntactic.SyntacticFail
import org.example.parser.syntactic.SyntacticSuccess
import semantic.SemanticAnalyzer

/**
 * The Parser class joins both analysers in a single class
 *
 * Takes the hassle of creating an instance for each analyser in the runner because it has its own instances of syntacticAnalyzer and semanticAnalyzer inside the class
 */
class Parser {
    private val syntacticAnalyzer = SyntacticAnalyzer()
    private val semanticAnalyzer = SemanticAnalyzer()

    fun parse(tokens: List<Token>): ASTNode {
        TODO()
//        return when (val syntacticResult = syntacticAnalyzer.build(tokens)) {
//            is SyntacticFail -> {
//                throw Exception("Error: ${syntacticResult.message}")
//            }
//            is SyntacticSuccess -> { semanticAnalyzer.analyze(syntacticResult.astNode) }
//        }
    }
}