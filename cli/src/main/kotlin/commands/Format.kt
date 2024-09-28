package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.formatter.CodeFormatter
import java.io.File

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
        try {
            CodeFormatter().format(inputFile.path, configFile.path)
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}