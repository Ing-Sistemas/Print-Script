package org.example

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.example.commands.Analyze
import org.example.commands.Execute
import org.example.commands.Format
import org.example.commands.Validate

class CLI : CliktCommand() {

    private val fileName by argument(help = "The file to use")
    private val config by argument(help = "JSON configuration").optional()
    private val version by option(help = "The version of program").required()

    private val versions = setOf("1.0", "1.1")

    override fun run() {
        // Validate the version
        if (version !in versions) {
            throw CliktError("Your version '$version' is not supported.")
        }

        // Validate linterConfig file extension
        if (config != null && !config!!.endsWith(".json")) {
            throw CliktError("Configuration file must be a .json file. Provided: $config")
        }

        // Validate fileName file extension
        if (!fileName.endsWith(".ps")) {
            throw CliktError("Input file must be a .ps file. Provided: $fileName")
        }

        currentContext.findOrSetObject { CLIContext(version, config, fileName) }
    }
}

data class CLIContext(
    val version: String,
    val config: String?,
    val fileName: String,
)

fun main(args: Array<String>) {
    val cli = CLI()
    cli
        .subcommands(Execute(), Analyze(), Format(), Validate())
        .main(args)
}