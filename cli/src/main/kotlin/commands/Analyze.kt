package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.cli.logic.AnalyzeLogic
import com.printscript.token.Token
import java.io.File

class Analyze : CliktCommand(
    name = "analyse",
    help = "Analyze a file",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
        val version = cliContext.version
        val linterConfig = cliContext.config
        val fileName = cliContext.fileName

        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        if (linterConfig == null) {
            throw CliktError("No linter config file provided.")
        }

        val configBaseDir = File("../linter/src/main/resources")
        val configFile = File(configBaseDir, linterConfig)

        val tokens = mutableListOf<Token>()

        try {
            val result = AnalyzeLogic().analyse(version, inputFile.inputStream(), configFile)
            if (result.isEmpty()) {
                echo("No linting errors found.")
            } else {
                throw CliktError("Failed lint check for $linterConfig with Error: ${result.fold("") { acc, s -> acc + s }}")
            }
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}