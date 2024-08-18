package org.example.parser

import Token
import org.example.ASTNode
import org.example.parser.syntactic.SyntacticAnalyzer
import semantic.SemanticAnalyzer


class Parser {
    private val syntacticAnalyzer = SyntacticAnalyzer()
    private val semanticAnalyzer = SemanticAnalyzer()

    fun parse(tokens: List<Token>): ASTNode {
        val ast = syntacticAnalyzer.buildAST(tokens)
        return semanticAnalyzer.analyze(ast)
    }
}