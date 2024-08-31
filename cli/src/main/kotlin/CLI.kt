package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.transformValues
import com.github.ajalt.clikt.parameters.options.versionOption
import org.example.commands.RunCommand
import java.io.InputStreamReader

class CLI : CliktCommand() {

    val v by option(help = "two vals").transformValues(2) { it[0] to it[1] }
    init {
        versionOption("1.0")
    }

    override fun run() {
        println("Type 'exit' to quit.")

        while (true) {
            print("> ")
            val input = readlnOrNull()

            if (input == null || input.lowercase() == "exit") {
                println("Exiting interactive mode.")
                break
            }

            try {
                val args = input.split(" ").toTypedArray()
                currentContext.invokedSubcommand?.main(args)
            } catch (e: Exception) {
                echo("Error: ${e.message}", err = true)
            }
        }
    }
}

fun main(args: Array<String>) {
    val inputReader = InputStreamReader(System.`in`)
    CLI()
        .subcommands(
            RunCommand(),
        ).main(args)
}