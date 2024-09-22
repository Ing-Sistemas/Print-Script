package org.example.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import org.example.CLIContext
import org.example.Runner
import providers.DefaultEnvProvider
import providers.DefaultInputProvider
import providers.DefaultOutPutProvider
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
            Runner(DefaultInputProvider(), DefaultOutPutProvider(), DefaultEnvProvider()).run(inputFile.inputStream(), version)
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}