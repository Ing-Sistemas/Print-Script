package org.example.commands

import Lexer
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import org.example.CLIContext
import org.example.parser.Parser
import java.io.File

class Validate : CliktCommand(
    name = "validate",
    help = "Validate a file",
) {

    override fun run() {
        val cliContext = currentContext.findObject<CLIContext>() ?: throw CliktError("Could not find CLIContext")
        val file = cliContext.fileName

        val baseDir = File("../cli/src/main/resources")
        val inputFile = File(baseDir, file)
        val tokens = mutableListOf<Token>()

        try {
            val bufferedReader = inputFile.bufferedReader()
            bufferedReader.use { reader ->
                reader.forEachLine { line ->
                    val lexer = Lexer()
                    val lineTokens = lexer.tokenize(line)
                    tokens.addAll(lineTokens)
                }
            }
            Parser().parse(tokens)
            echo("successfully validated")
        } catch (e: Exception) {
            throw CliktError(e.message)
        }
    }
}