package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import configurations.ConfigLoader
import linters.StaticCodeAnalyzer
import java.io.File

class Analyse : CliktCommand() {

    private val linterConfig by argument(help = "Linter configuration for check")
    private val fileName by argument(help = "The file to execute")
    private val version by option(help = "The version to execute").double().required()

    override fun run() {
        if (version != 1.0) {
            throw CliktError("Your version '$version' is not supported.")
        }

        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, fileName)

        val configBaseDir = File("../linter/src/main/resources")
        val configFile = File(configBaseDir, linterConfig)

        val tokens = mutableListOf<Token>()

        try {
            val configLoader = ConfigLoader
            val config = configLoader.loadConfiguration(configFile.path)
            val linter = StaticCodeAnalyzer(config)
            val bufferedReader = inputFile.bufferedReader()
            bufferedReader.use { reader ->
                reader.forEachLine { line ->
                    val lexer = Lexer()
                    val lineTokens = lexer.tokenize(line)
                    tokens.addAll(lineTokens)
                }
            }
            val result = linter.analyze(tokens)
            if (result.isNotEmpty()) {
                throw CliktError("Failed lint check for $linterConfig")
            }
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}