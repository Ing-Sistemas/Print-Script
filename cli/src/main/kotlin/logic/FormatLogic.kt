package com.printscript.cli.logic

import com.printscript.formatter.CodeFormatter
import com.printscript.formatter.config.FormatterConfig
import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
import com.printscript.runner.ReaderIterator
import java.io.File
import java.io.FileWriter

class FormatLogic {
    fun format(inputFile: File, config: FormatterConfig, version: String) {
        val tokens = Lexer(version).tokenize(ReaderIterator().getLineIterator(inputFile.inputStream()))
        val ast = ASTIterator(tokens, Parser())
        FileWriter(inputFile).use {
            while (ast.hasNext()) {
                val astNode = ast.next()
                val formattedString = CodeFormatter().format(astNode, config)
                it.write(formattedString)
            }
        }
    }
}