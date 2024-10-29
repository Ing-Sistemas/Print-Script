package com.printscript.cli.logic

import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
import com.printscript.runner.ReaderIterator
import java.io.InputStream

class ValidateLogic {
    fun validate(version: String, inputFile: InputStream) {
        val readerIterator = ReaderIterator().getLineIterator(inputFile)
        val tokens = Lexer(version).tokenize(readerIterator)
        val astIterator = ASTIterator(tokens, Parser())
        while (astIterator.hasNext()) {
            astIterator.next()
        }
    }
}