package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import configurations.ConfigLoader
import linters.StaticCodeAnalyzer
import org.example.CLIContext
import org.example.ReaderIterator
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
            val configLoader = ConfigLoader
            val config = configLoader.loadConfiguration(configFile.path)
            val linter = StaticCodeAnalyzer(config, version)
            val readerIterator = ReaderIterator().getLineIterator(inputFile.inputStream())
            val lexer = Lexer(version).tokenize(readerIterator)
            while (lexer.hasNext()) {
                val token = lexer.next()
                tokens.add(token)
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