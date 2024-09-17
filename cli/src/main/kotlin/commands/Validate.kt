package org.example.commands

import Lexer
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import org.example.CLIContext
import org.example.ReaderIterator
import org.example.parser.Parser
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