package org.example.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import configurations.ConfigLoader
import linters.StaticCodeAnalyzer
import org.example.CLIContext
import org.example.Lexer
import org.example.Token
import java.io.File

class Analyse : CliktCommand(
    name = "analyse",
    help = "Analyse a file",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
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
                println(result.size)
                throw CliktError("Failed lint check for $linterConfig with Error: ${result.fold("") { acc, s -> acc + s }}")
            }
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}