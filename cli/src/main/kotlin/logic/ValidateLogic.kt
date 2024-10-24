package com.printscript.cli.logic

import com.printscript.ast.ASTNode
import com.printscript.lexer.Lexer
import com.printscript.parser.Parser
import com.printscript.runner.ReaderIterator
import java.io.InputStream

class ValidateLogic {
    fun validate(version: String, inputFile: InputStream): ASTNode {
        val readerIterator = ReaderIterator().getLineIterator(inputFile)
        val tokens = Lexer(version).tokenize(readerIterator)
        return Parser().parse(tokens)
    }
}