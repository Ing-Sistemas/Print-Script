package org.example

import Lexer
import semantic.SemanticAnalyzer
import Token
import org.example.parser.syntactic.SyntacticAnalyzer

class Runner {
    private val lexer = Lexer()
    private val syntacticAnalyzer = SyntacticAnalyzer()
    private val semanticAnalyzer = SemanticAnalyzer()
    private val interpreter = Interpreter()

    fun run(code: String): Any {
        val tokens = lexer.tokenize(code)
        println(tokens)
        val ast = syntacticAnalyzer.buildAST(tokens)
        val ast2 = semanticAnalyzer.analyze(ast)
        val interpreted = interpreter.interpret(ast2)
        return interpreted
    }

    //----------METHODS TO DEBUG
    fun getTokens(code: String): List<Token> {
        return lexer.tokenize(code)
    }
    fun getAST(code: String): ASTNode {
        val tokens = lexer.tokenize(code)
        return syntacticAnalyzer.buildAST(tokens)
    }
    fun getInterpreted(code: String): Any {
        val tokens = lexer.tokenize(code)
        val ast = syntacticAnalyzer.buildAST(tokens)
        val ast2 = semanticAnalyzer.analyze(ast)
        return interpreter.interpret(ast2)
    }
}