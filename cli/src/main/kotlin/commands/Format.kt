package org.example.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import org.example.CodeFormatter
import java.io.File

class Format : CliktCommand(help = "Formats a file") {

    private val formatConfig by argument(help = "Linter configuration for check")
    private val fileName by argument(help = "The file to execute")
    private val version by option(help = "The version to execute").double().required()

    override fun run() {
        if (version != 1.0) {
            throw CliktError("Your version '$version' is not supported.")
        }
        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        val configBaseDir = File("../formatter/src/test/resources")
        val configFile = File(configBaseDir, formatConfig)
        try {
            CodeFormatter().format(inputFile.path, configFile.path)
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}