package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import interpreters.Interpreter
import org.example.CLIContext
import org.example.Runner
import org.example.parser.Parser
import utils.Storage
import java.io.File

class Execute : CliktCommand(
    name = "execute",
    help = "Executes a command",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
        val fileName = cliContext.fileName
        val version = cliContext.version
        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        echo("Executing input...")
        try {
            Runner().run(inputFile.inputStream(), version)
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}