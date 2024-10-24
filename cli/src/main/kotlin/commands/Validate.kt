package com.printscript.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.printscript.cli.CLIContext
import com.printscript.cli.logic.ValidateLogic
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
            echo("Validating file...")
            ValidateLogic().validate(version, inputFile)
            echo("File validated successfully.")
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}