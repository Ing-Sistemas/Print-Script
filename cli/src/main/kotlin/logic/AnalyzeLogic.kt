package com.printscript.cli.logic

import com.printscript.lexer.Lexer
import com.printscript.linter.configurations.ConfigLoader
import com.printscript.linter.linters.StaticCodeAnalyzer
import com.printscript.runner.ReaderIterator
import com.printscript.token.Token
import java.io.File

class AnalyzeLogic {
    fun analyse(version: String, linterConfig: String, configFile: File, inputFile: File): List<String> {
        val tokens = mutableListOf<Token>()
        val config = ConfigLoader.loadConfiguration(configFile.path)
        val linter = StaticCodeAnalyzer(config, version)
        val readerIterator = ReaderIterator().getLineIterator(inputFile.inputStream())
        val tokenIterator = Lexer(version).tokenize(readerIterator)

        while (tokenIterator.hasNext()) {
            val token = tokenIterator.next()
            tokens.add(token)
        }

        return linter.analyze(tokens)
    }
}