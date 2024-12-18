package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.cli.logic.FormatLogic
import com.printscript.formatter.config.ConfigJsonReader
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
        val config = ConfigJsonReader().convertJsonIntoFormatterConfig(configFile.path)
        try {
            FormatLogic().format(cliContext.version, inputFile, config)
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}