package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.formatter.CodeFormatter
import com.printscript.formatter.config.ConfigJsonReader
import com.printscript.lexer.Lexer
import com.printscript.parser.ASTIterator
import com.printscript.parser.Parser
import com.printscript.runner.ReaderIterator
import java.io.File
import java.io.FileWriter

class Format : CliktCommand(
    name = "format",
    help = "Formats a file",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")

        val formatConfig = cliContext.config
        val fileName = cliContext.fileName
        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        val configBaseDir = File("../formatter/src/test/resources")

        if (formatConfig == null) {
            throw CliktError("No format config file provided.")
        }

        val configFile = File(configBaseDir, formatConfig)
        val config = ConfigJsonReader().convertJsonIntoFormatterConfig(configFile.path)
        try {
            val tokens = Lexer(cliContext.version).tokenize(ReaderIterator().getLineIterator(inputFile.inputStream()))
            val ast = ASTIterator(tokens, Parser())
            FileWriter(inputFile).use {
                while (ast.hasNext()) {
                    val astNode = ast.next()
                    val formattedString = CodeFormatter().format(astNode, config)
                    it.write(formattedString)
                }
            }
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}