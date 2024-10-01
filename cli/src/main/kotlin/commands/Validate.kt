package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.lexer.Lexer
import com.printscript.parser.Parser
import com.printscript.runner.ReaderIterator
import java.io.File

class Validate : CliktCommand(
    name = "validate",
    help = "Validate a file",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
        val file = cliContext.fileName
        val version = cliContext.version
        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, file)

        try {
            val readerIterator = ReaderIterator().getLineIterator(inputFile.inputStream())
            val tokens = Lexer(version).tokenize(readerIterator)
            echo("Parsing $file ...")
            Parser().parse(tokens)
            echo("successfully validated")
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}